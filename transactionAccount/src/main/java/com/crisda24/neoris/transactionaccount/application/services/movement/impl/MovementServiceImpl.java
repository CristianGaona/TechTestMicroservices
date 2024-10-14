package com.crisda24.neoris.transactionaccount.application.services.movement.impl;

import com.crisda24.neoris.transactionaccount.application.common.dtos.client.ClientInfoResponseDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.movement.interfaces.MovementRangeDateDtoI;
import com.crisda24.neoris.transactionaccount.application.common.dtos.movement.MovementRangeDatesDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.movement.MovementRequestDto;
import com.crisda24.neoris.transactionaccount.application.common.mappers.movement.MovementMapper;
import com.crisda24.neoris.transactionaccount.application.services.movement.MovementService;
import com.crisda24.neoris.transactionaccount.infrastructure.repositories.movement.MovementRepository;
import com.crisda24.neoris.transactionaccount.application.services.account.AccountService;
import com.crisda24.neoris.transactionaccount.domain.exceptions.GeneralException;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.domain.models.Movement;
import com.crisda24.neoris.transactionaccount.infrastructure.rabbit.PublisherService;
import com.crisda24.neoris.transactionaccount.infrastructure.utils.GeneralUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountService accountService;
    private final PublisherService publisherService;
    private final MovementMapper movementMapper;

    public MovementServiceImpl(MovementRepository movementRepository, AccountService accountService, PublisherService publisherService, MovementMapper movementMapper) {
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

        //Check movementType
        if (movement.getMovementType().toString().equals("DEPOSITO")) {
            if (movement.getMovement().compareTo(new BigDecimal(0)) <= 0) {
                throw new GeneralException("deposits cannot be negative", HttpStatus.BAD_REQUEST);
            }
            movement.setBalance(accountCheck.getInitBalance().add(movement.getMovement()));
            accountCheck.setInitBalance(accountCheck.getInitBalance().add(movement.getMovement()));

        } else {
            if (movement.getMovement().compareTo(new BigDecimal(0)) >= 0) {
                throw new GeneralException("witdrawals cannot be positive", HttpStatus.BAD_REQUEST);
            }

            if (movement.getMovement().abs().compareTo(accountCheck.getInitBalance()) > 0) {
                throw new GeneralException("Balance not available", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            movement.setBalance(accountCheck.getInitBalance().add(movement.getMovement()));
            accountCheck.setInitBalance(accountCheck.getInitBalance().add(movement.getMovement()));
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
        List<MovementRangeDateDtoI> movements = null;
        if(accountNumber != null && clientId != null ){
            System.out.println("CASO 1");
            movements = movementRepository.retrieveMovemenstByDates(startDate, endDate, accountNumber, clientId);
        }else if(accountNumber != null){
            System.out.println("CASO 2");
            movements = movementRepository.retrieveMovemenstByDates(startDate, endDate, accountNumber);
        }else{
            System.out.println("CASO 3");
            movements = movementRepository.retrieveMovemenstByDates(startDate, endDate, clientId);
        }

        if(Objects.isNull(movements)){
            throw new GeneralException("No movements found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map<Long, String> clientNameMap = clientInfoList.stream()
                .collect(Collectors.toMap(ClientInfoResponseDto::getId, ClientInfoResponseDto::getName));

        List<MovementRangeDatesDto> result = new ArrayList<>();
        result = movements.stream()
                .map(movement -> {
                    MovementRangeDatesDto movementDto = new MovementRangeDatesDto();
                    movementDto.setValue(movement.getValue());
                    movementDto.setType(movement.getType());
                    movementDto.setDate(movement.getDate());
                    movementDto.setAccountNumber(movement.getAccountNumber());
                    movementDto.setInitBalance(movement.getInitBalance());
                    movementDto.setMovement(movement.getMovement());
                    movementDto.setBalance(movement.getBalance());
                    movementDto.setClientName(clientNameMap.get(movement.getClientId()));
                    return movementDto;
                })
                .collect(Collectors.toList());

        return result;
    }


}
