package com.crisda24.neoris.transactionaccount.application.output.port;

import com.crisda24.neoris.transactionaccount.domain.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    public Account findByAccountNumber(String accountNumber);
    Integer countByIdClient(Long idClient);
    public Account save(Account account);
    public void delete(Account account);
    public Optional<Account> findById(Long id);
    public List<Account> findAll();
    public void deleteAll();
    public Boolean existsById(Long id);
}
