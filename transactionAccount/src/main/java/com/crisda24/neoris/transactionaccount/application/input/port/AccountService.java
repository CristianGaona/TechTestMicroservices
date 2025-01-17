package com.crisda24.neoris.transactionaccount.application.input.port;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.account.AccountRequestDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.account.AccountRequestPatchDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.account.MergedAccountInfoDto;
import com.crisda24.neoris.transactionaccount.infrastructure.input.adapter.exceptions.GeneralException;
import com.crisda24.neoris.transactionaccount.domain.models.Account;

import java.util.List;
import java.util.Optional;

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
