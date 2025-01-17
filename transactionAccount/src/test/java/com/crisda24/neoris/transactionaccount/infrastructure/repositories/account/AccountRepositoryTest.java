package com.crisda24.neoris.transactionaccount.infrastructure.repositories.account;

import com.crisda24.neoris.transactionaccount.application.output.port.AccountRepository;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.AccountRepositoryAdapter;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.entity.AccountEntity;
import com.crisda24.neoris.transactionaccount.mockData.AccountMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AccountRepositoryAdapter.class)
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Account account = AccountMock.createAccountEntity();
        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(account, accountEntity);
        if (account.getIdAccount() == null) {
            testEntityManager.persist(accountEntity);
        } else {
            testEntityManager.merge(accountEntity);
        }
        testEntityManager.flush();

    }

    @Test
    public void findByAccountNumberSuccess(){
        Account account = accountRepository.findByAccountNumber("123456789");
        assertNotNull(account);
        assertEquals("123456789", account.getAccountNumber());
    }


    @Test
    public void findByAccountNumberNotFound(){
        Account account = accountRepository.findByAccountNumber("123456781");
        assertNull(account);
    }


    @Test
    public void countByIdClientSuccess(){
        Integer numberAccountsByClient = accountRepository.countByIdClient(1L);
        assertNotNull(numberAccountsByClient);
        assertEquals(1, numberAccountsByClient);
    }

    @Test
    public void countByIdClientNotFound(){
        Integer numberAccountsByClient = accountRepository.countByIdClient(2L);
        assertNotEquals(1, numberAccountsByClient);
    }

}