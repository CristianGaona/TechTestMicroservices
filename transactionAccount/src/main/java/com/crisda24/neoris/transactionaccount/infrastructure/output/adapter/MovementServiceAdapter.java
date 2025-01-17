package com.crisda24.neoris.transactionaccount.infrastructure.output.adapter;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.client.ClientInfoResponseDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.interfaces.MovementRangeDateDtoI;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.MovementRangeDatesDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.MovementRequestDto;
import com.crisda24.neoris.transactionaccount.domain.common.mappers.movement.MovementMapper;
import com.crisda24.neoris.transactionaccount.application.input.port.MovementService;
import com.crisda24.neoris.transactionaccount.application.input.port.AccountService;
import com.crisda24.neoris.transactionaccount.application.output.port.MovementRepository;
import com.crisda24.neoris.transactionaccount.domain.enums.MovementType;
import com.crisda24.neoris.transactionaccount.infrastructure.input.adapter.exceptions.GeneralException;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.domain.models.Movement;
import com.crisda24.neoris.transactionaccount.infrastructure.utils.GeneralUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovementServiceAdapter implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountService accountService;
    private final PublisherAdapter publisherService;
    private final MovementMapper movementMapper;

    public MovementServiceAdapter(MovementRepository movementRepository, AccountService accountService, PublisherAdapter publisherService, MovementMapper movementMapper) {
        this.movementRepository = movementRepository;
        this.accountService = accountService;
        this.publisherService = publisherService;
        this.movementMapper = movementMapper;
    }

    @Override
    public void create(MovementRequestDto movementRequestDto) {

        //Check account number
        Account accountCheck = accountService.accountExists(movementRequestDto.getAccountNumber());
        Movement movement = movementMapper.dtoToEntity(movementRequestDto);
        movement.setAccount(accountCheck);
        BigDecimal movementBalance = movementRepository.findLastBalanceByAccountId(accountCheck.getIdAccount());

        if(ObjectUtils.isEmpty(movementBalance)){
            movementBalance = accountCheck.getInitBalance();
        }

        if (movement.getMovementType().equals(MovementType.DEPOSITO)) {
            if (movement.getMovement().compareTo(new BigDecimal(0)) <= 0) {
                throw new GeneralException("deposits cannot be negative", HttpStatus.BAD_REQUEST);
            }
            movement.setBalance(movementBalance.add(movement.getMovement()));
        }
        else {
            if (movement.getMovement().compareTo(new BigDecimal(0)) >= 0) {
                throw new GeneralException("witdrawals cannot be positive", HttpStatus.BAD_REQUEST);
            }

            if (movement.getMovement().abs().compareTo(movementBalance) > 0) {
                throw new GeneralException("Balance not available", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            movement.setBalance(movementBalance.add(movement.getMovement()));
        }

        movementRepository.save(movement);

    }

    @Override
    public List<MovementRangeDatesDto> retrieveMovemenstByDates(String startDate, String endDate, String accountNumber, Long clientId) {

        if(!GeneralUtils.isValidDateRange(startDate, endDate)){
            throw new GeneralException("Invalid date range: " + startDate + " - " + endDate, HttpStatus.BAD_REQUEST);
        }
        List<Long> clientIds = accountService.retrieveClientIds();
        List<ClientInfoResponseDto> clientInfoList = publisherService.sendAndReceiveClientsInfo(clientIds);

        List<MovementRangeDateDtoI> movements = retrieveMovementsBasedOnParams(startDate, endDate, accountNumber, clientId);

        if(Objects.isNull(movements)){
            throw new GeneralException("No movements found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map<Long, String> clientNameMap = clientInfoList.stream()
                .collect(Collectors.toMap(ClientInfoResponseDto::getId, ClientInfoResponseDto::getName));


        return movements.stream()
                .map(movement -> {
                    MovementRangeDatesDto movementDto = new MovementRangeDatesDto();
                    movementDto.setValue(movement.getValue());
                    movementDto.setType(movement.getType());
                    movementDto.setDate(movement.getDate());
                    movementDto.setAccountNumber(movement.getAccountNumber());
                    movementDto.setPreviusBalance(movement.getInitBalance());
                    movementDto.setMovement(movement.getMovement());
                    movementDto.setBalance(movement.getBalance());
                    movementDto.setClientName(clientNameMap.get(movement.getClientId()));
                    return movementDto;
                })
                .collect(Collectors.toList());
    }

    private List<MovementRangeDateDtoI> retrieveMovementsBasedOnParams(String startDate, String endDate, String accountNumber, Long clientId) {
        if(accountNumber != null && clientId != null){
            return movementRepository.retrieveMovemenstByDates(startDate, endDate, accountNumber, clientId);
        } else if(accountNumber != null) {
            return movementRepository.retrieveMovemenstByDates(startDate, endDate, accountNumber);
        } else {
            return movementRepository.retrieveMovemenstByDates(startDate, endDate, clientId);
        }
    }

}
