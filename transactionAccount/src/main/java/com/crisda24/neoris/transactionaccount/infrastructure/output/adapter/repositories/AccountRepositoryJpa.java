package com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories;

import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositoryJpa extends JpaRepository<AccountEntity, Long> {

    AccountEntity findByAccountNumber(String accountNumber);

    Integer countByIdClient(Long idClient);
}
