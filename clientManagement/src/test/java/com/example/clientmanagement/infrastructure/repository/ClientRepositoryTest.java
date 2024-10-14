package com.example.clientmanagement.infrastructure.repository;

import com.example.clientmanagement.domain.model.Client;
import com.example.clientmanagement.mockData.ClientMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Client client = ClientMock.createClientEntity();
        testEntityManager.persist(client);
    }

    @Test
    public void findByIdentificationSuccess(){
        Client client = clientRepository.findByIdentification("11060613458");
        assertEquals(client.getIdentification(), "11060613458");
    }

    @Test
    public void findByIdentificationNotFound(){
        Client client = clientRepository.findByIdentification("11060613455");
        assertNull(client);
    }

    @Test
    public void findByPhoneNumberSuccess(){
        Client client = clientRepository.findByPhoneNumber("0985880290");
        assertEquals(client.getPhoneNumber(), "0985880290");
    }

    @Test
    public void findByPhoneNumberNotFound(){
        Client client = clientRepository.findByPhoneNumber("11060613455");
        assertNull(client);
    }

}