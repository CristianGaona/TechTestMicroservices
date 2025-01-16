package com.example.clientmanagement.infrastructure.rabbit;

import com.example.clientmanagement.domain.common.dto.messages.ClientIdListMessage;
import com.example.clientmanagement.domain.model.Client;
import com.example.clientmanagement.infrastructure.input.adapter.ConsumerAdapter;
import com.example.clientmanagement.infrastructure.output.adapter.repository.ClientRepositoryJpa;
import com.example.clientmanagement.mockData.ClientMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ConsumerServiceTest {

    @Container
    static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.9-management")
            .withExposedPorts(5672);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ClientRepositoryJpa clientRepository;

    @Autowired
    private ConsumerAdapter consumerService;

    private Client client;

    @BeforeEach
    void setup() {
        client = ClientMock.createClientEntity();
        clientRepository.save(client);
    }

    @Test
    void testSendAndReceiveClientsInfo() throws Exception {

        ClientIdListMessage message = new ClientIdListMessage();
        message.setIds(Collections.singletonList(1L));

        String messageJson = mapper.writeValueAsString(message);

        rabbitTemplate.convertAndSend("queue.account.client.info", messageJson);

        String response = consumerService.sendAndReceiveClientsInfo(messageJson);
        assertNotNull(response);
        assertTrue(response.contains("Cristian Gaona"));
    }
}