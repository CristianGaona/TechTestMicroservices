package com.crisda24.neoris.transactionaccount.infrastructure.output.adapter;

import com.crisda24.neoris.transactionaccount.application.output.port.AccountRepository;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.AccountRepositoryJpa;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.entity.AccountEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {

    AccountRepositoryJpa accountRepositoryJpa;

    public AccountRepositoryAdapter(AccountRepositoryJpa accountRepositoryJpa) {
        this.accountRepositoryJpa = accountRepositoryJpa;
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        AccountEntity accountEntity = accountRepositoryJpa.findByAccountNumber(accountNumber);
        if(ObjectUtils.isEmpty(accountEntity)){
            return null;
        }
        Account account = new Account();
        BeanUtils.copyProperties(accountEntity, account);
        return account;
    }

    @Override
    public Integer countByIdClient(Long idClient) {
        return accountRepositoryJpa.countByIdClient(idClient);
    }


    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(account, accountEntity);
        accountRepositoryJpa.save(accountEntity);
        BeanUtils.copyProperties(accountEntity, account);
        return account;
    }

    @Override
    public void delete(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(account, accountEntity);
        accountRepositoryJpa.delete(accountEntity);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepositoryJpa.findById(id)
                .map(entity ->{
                    Account account = new Account();
                    BeanUtils.copyProperties(entity, account);
                    return account;
                });
    }

    @Override
    public List<Account> findAll() {
        List<AccountEntity> accountEntities = accountRepositoryJpa.findAll();
        if(ObjectUtils.isEmpty(accountEntities)){
            return Collections.emptyList();
        }
        return accountEntities.stream()
                .map(entity ->{
                    Account account = new Account();
                    BeanUtils.copyProperties(entity, account);
                    return account;
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        accountRepositoryJpa.deleteAll();
    }

    @Override
    public Boolean existsById(Long id) {
        return accountRepositoryJpa.existsById(id);
    }
}
