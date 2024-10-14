package com.crisda24.neoris.transactionaccount.application.services.account.impl;

import com.crisda24.neoris.transactionaccount.application.common.dtos.account.AccountRequestDto;
import com.crisda24.neoris.transactionaccount.application.common.mappers.account.AccountMapper;
import com.crisda24.neoris.transactionaccount.application.services.account.AccountService;
import com.crisda24.neoris.transactionaccount.domain.exceptions.GeneralException;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.infrastructure.rabbit.PublisherService;
import com.crisda24.neoris.transactionaccount.infrastructure.repositories.account.AccountRepository;
import com.crisda24.neoris.transactionaccount.mockData.AccountMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@SpringBootTest
class AccountServiceImplTest {
    @MockBean
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private PublisherService publisherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccountSuccess(){
        Account account = AccountMock.createAccountEntity();
        AccountRequestDto accountRequestDto = AccountMock.createAccountRequestDto();

        when(accountMapper.dtoToEntity(accountRequestDto)).thenReturn(account);
        when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(null);
        when(publisherService.sendAndReceiveClientExists(1L)).thenReturn(true);

        accountService.createAccount(accountRequestDto);

        verify(accountRepository).save(account);

    }

    @Test
    void createAccountWhenClientNotExists(){
        Account account = AccountMock.createAccountEntity();
        AccountRequestDto accountRequestDto = AccountMock.createAccountRequestDto();

        when(accountMapper.dtoToEntity(accountRequestDto)).thenReturn(account);
        when(publisherService.sendAndReceiveClientExists(1L)).thenReturn(false);

        GeneralException exception = assertThrows(GeneralException.class, () -> accountService.createAccount(accountRequestDto));
        assertEquals("Client does not exist", exception.getMessage());

    }

    @Test
    void createAccountBadRequest(){
        Account account = AccountMock.createAccountEntity();
        AccountRequestDto accountRequestDto = AccountMock.createAccountRequestDto();

        when(accountMapper.dtoToEntity(accountRequestDto)).thenReturn(account);
        when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(account);

        GeneralException thrown = assertThrows(GeneralException.class, () -> accountService.createAccount(accountRequestDto));
        assertEquals("Account number is used, please try again", thrown.getMessage());

        verify(accountRepository).findByAccountNumber(eq(account.getAccountNumber()));
        verifyNoMoreInteractions(accountMapper, accountRepository);

    }

}