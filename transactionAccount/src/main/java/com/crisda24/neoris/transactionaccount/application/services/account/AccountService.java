package com.crisda24.neoris.transactionaccount.application.services.account;

import com.crisda24.neoris.transactionaccount.application.common.dtos.account.AccountRequestDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.account.AccountRequestPatchDto;
import com.crisda24.neoris.transactionaccount.application.common.dtos.account.MergedAccountInfoDto;
import com.crisda24.neoris.transactionaccount.domain.exceptions.GeneralException;
import com.crisda24.neoris.transactionaccount.domain.models.Account;

import java.util.List;

public interface AccountService {

    public void createAccount(AccountRequestDto accountDto) throws GeneralException;
    public List<MergedAccountInfoDto> retrieveAccounts();
    public Account accountExists(String accountNumber);
    public List<Long> retrieveClientIds();
    public void delete (String accountNumber);
    public Integer totalAccountsByClient(Long clientId);
    public MergedAccountInfoDto retrieveAccountByAccountNumber(String accountNumber);
    public Account accountPatch (String accountNumber, AccountRequestPatchDto accountRequestPatchDto);
}
