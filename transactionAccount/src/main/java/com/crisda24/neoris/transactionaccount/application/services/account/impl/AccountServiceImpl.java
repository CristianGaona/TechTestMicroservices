package com.crisda24.neoris.transactionaccount.application.services.account.impl;
import com.crisda24.neoris.transactionaccount.application.common.dtos.account.AccountRequestPatchDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.account.AccountRequestDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.client.ClientInfoResponseDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.account.MergedAccountInfoDto;
import com.crisda24.neoris.transactionaccount.application.common.mappers.account.AccountMapper;
import com.crisda24.neoris.transactionaccount.application.services.account.AccountService;
import com.crisda24.neoris.transactionaccount.infrastructure.repositories.account.AccountRepository;
import com.crisda24.neoris.transactionaccount.domain.exceptions.GeneralException;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.infrastructure.rabbit.PublisherService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PublisherService publisherService;
    private final AccountMapper accountMapper;


    public AccountServiceImpl(AccountRepository accountRepository, PublisherService publisherService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.publisherService = publisherService;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional
    public void createAccount(AccountRequestDto accountDto) throws GeneralException {
        Account accountCheck = accountRepository.findByAccountNumber(accountDto.getAccountNumber());
        if(accountCheck != null){
            throw new GeneralException("Account number is used, please try again", HttpStatus.BAD_REQUEST);
        }

        Boolean clientExists = publisherService.sendAndReceiveClientExists(accountDto.getIdClient());
        if(!clientExists){
            throw new GeneralException("Client does not exist", HttpStatus.BAD_REQUEST);
        }

        Account account = accountMapper.dtoToEntity(accountDto);
        accountRepository.save(account);
    }

    @Override
    public List<MergedAccountInfoDto> retrieveAccounts() {
        List<Account> accountList = accountRepository.findAll();
        if(accountList.isEmpty()){
            throw new GeneralException("No accounts registered", HttpStatus.NOT_FOUND);
        }
        List<Long> clientIds = accountList.stream()
                .map(Account::getIdClient)
                .distinct()
                .toList();

        List<ClientInfoResponseDto> clientInfoList = publisherService.sendAndReceiveClientsInfo(clientIds);

        Map<Long, ClientInfoResponseDto> clientInfoMap = clientInfoList.stream()
                .collect(Collectors.toMap(ClientInfoResponseDto::getId, clientInfo -> clientInfo));

        return accountList.stream()
                .map(account -> {
                    Long clientId = account.getIdClient();
                    ClientInfoResponseDto clientInfo = clientInfoMap.get(clientId);
                    if(Objects.nonNull(clientInfo)){
                        return accountMapper.entityIToDto(account, clientInfo.getName());
                    }
                    return null;
                })
                .toList();
    }

    @Override
    public Account accountExists(String accountNumber) {
        Account accountCheck = accountRepository.findByAccountNumber(accountNumber);
        if(Objects.isNull(accountCheck)){
            throw new GeneralException("Account number not exists", HttpStatus.BAD_REQUEST);
        }
        return accountCheck;
    }

    @Override
    public List<Long> retrieveClientIds() {
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream()
                .map(Account::getIdClient)
                .distinct()
                .toList();
    }

    @Override
    @Transactional
    public void delete(String accountNumber) {
        Account account = this.accountExists(accountNumber);
        accountRepository.delete(account);
    }

    @Override
    public Integer totalAccountsByClient(Long clientId) {
        Integer accountsByClient = accountRepository.countByIdClient(clientId);
        if(accountsByClient != null){
            return accountsByClient;
        }
        return -1;
    }

    @Override
    public MergedAccountInfoDto retrieveAccountByAccountNumber(String accountNumber) {
        Account account = this.accountExists(accountNumber);

        List<Long> clientId = new ArrayList<>();
        clientId.add(account.getIdClient());
        List<ClientInfoResponseDto> clientInfoList = publisherService.sendAndReceiveClientsInfo(clientId);

        if(Objects.isNull(clientInfoList)){
            throw new GeneralException("Account number not found", HttpStatus.BAD_REQUEST);
        }

        return accountMapper.entityToDto(account, clientInfoList.get(0).getName());
    }

    @Override
    @Transactional
    public Account accountPatch(String accountNumber, AccountRequestPatchDto accountRequestPatchDto) {
        Account accountExists= this.accountExists(accountNumber);
        if(accountRequestPatchDto.getAccountNumber()!=null){
            accountExists = this.accountExists(accountNumber);
            if(!Objects.equals(accountExists.getAccountNumber(), accountRequestPatchDto.getAccountNumber())){
                throw new GeneralException("Account number already exists, please try again", HttpStatus.BAD_REQUEST);
            }
        }
        accountRequestPatchDto.setIdAccount(accountExists.getIdAccount());
        accountExists = accountMapper.dtoPatchToEntity(accountExists, accountRequestPatchDto);
        return accountRepository.save(accountExists);
    }
}
