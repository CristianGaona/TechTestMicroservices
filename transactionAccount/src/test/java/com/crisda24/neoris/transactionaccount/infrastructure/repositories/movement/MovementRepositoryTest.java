package com.crisda24.neoris.transactionaccount.infrastructure.repositories.movement;

import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.domain.models.Movement;
import com.crisda24.neoris.transactionaccount.infrastructure.repositories.account.AccountRepository;
import com.crisda24.neoris.transactionaccount.mockData.AccountMock;
import com.crisda24.neoris.transactionaccount.mockData.MovementMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
class MovementRepositoryTest {

    @Autowired
    MovementRepository movementRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Movement movement = MovementMock.createMovementEntity();
        testEntityManager.persist(movement);
    }


}