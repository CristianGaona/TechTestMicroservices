package com.crisda24.neoris.transactionaccount.mockData;

import com.crisda24.neoris.transactionaccount.application.common.dtos.account.AccountRequestDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.account.AccountRequestPatchDto;
import com.crisda24.neoris.transactionaccount.domain.enums.AccountType;
import com.crisda24.neoris.transactionaccount.domain.models.Account;

import java.math.BigDecimal;

public class AccountMock {
    public static Account createAccountEntity() {
        Account account = new Account();
        account.setIdAccount(1L);
        account.setAccountType(AccountType.AHORRO);
        account.setAccountNumber("123456789");
        account.setInitBalance(new BigDecimal(100));
        account.setIdClient(1L);
        account.setStatus(true);
        //account.setMovements(Collections.singletonList(MovementMock.createMovementEntity()));
        return account;
    }

    public static AccountRequestDto createAccountRequestDto() {
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        accountRequestDto.setIdAccount(1L);
        accountRequestDto.setAccountNumber("123456789");
        accountRequestDto.setAccountType("AHHORRO");
        accountRequestDto.setInitBalance(new BigDecimal(100));
        accountRequestDto.setIdClient(1L);
        accountRequestDto.setStatus(true);
        return accountRequestDto;
    }

    public static AccountRequestPatchDto createAccountRequestPatchDto() {
        AccountRequestPatchDto accountRequestPatchDto = new AccountRequestPatchDto();
        accountRequestPatchDto.setStatus(false);
        return accountRequestPatchDto;
    }

}
