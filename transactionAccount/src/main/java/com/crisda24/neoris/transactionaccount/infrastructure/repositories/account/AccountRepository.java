package com.crisda24.neoris.transactionaccount.infrastructure.repositories.account;

import com.crisda24.neoris.transactionaccount.application.common.dtos.account.interfaces.AccountListDtoI;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);

    Integer countByIdClient(Long idClient);
}
