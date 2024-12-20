package com.example.clientmanagement.infrastructure.rabbit;

import com.example.clientmanagement.common.dto.ClientInfoResponseDto;
import com.example.clientmanagement.common.dto.interfaces.ClientInfoResponseDtoI;
import com.example.clientmanagement.common.dto.messages.ClientIdListMessage;
import com.example.clientmanagement.application.service.ClientService;
import com.example.clientmanagement.domain.exception.GeneralException;
import com.example.clientmanagement.domain.model.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumerService {

    private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    private final ObjectMapper mapper;
    private final ClientService clientService;

    private static final String QUEUE_CLIENT_INFO = "queue.account.client.info";
    private static final String QUEUE_CLIENT_EXISTS = "queue.client.exists";

    public ConsumerService(ObjectMapper mapper, ClientService clientService) {
        this.mapper = mapper;
        this.clientService = clientService;
    }

    @RabbitListener(queues = QUEUE_CLIENT_INFO, containerFactory = "jsaFactory")
    public String sendAndReceiveClientsInfo(String message) {

        logger.info("Received message: {}", message);
        ClientIdListMessage clientIdListMessage = new ClientIdListMessage();
        try {
            clientIdListMessage = mapper.readValue(message, ClientIdListMessage.class);
        } catch (Exception e) {
            logger.error("receiveClientsInfo - Error parsing message: {}", e.getMessage());
            throw  new GeneralException("An unexpected error has occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<ClientInfoResponseDtoI> clientInfoResponseDtoI = clientService.findClientsInfoByIds(clientIdListMessage.getIds());
        List<ClientInfoResponseDto> list = clientInfoResponseDtoI.stream()
                .map(client -> new ClientInfoResponseDto(client.getId(), client.getName()))
                .toList();
        try {
            return mapper.writeValueAsString(list);
        } catch (Exception e) {
            logger.error("receiveClientsInfo - Error parsing response: {}", e.getMessage());
            throw  new GeneralException("An unexpected error has occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RabbitListener(queues = QUEUE_CLIENT_EXISTS, containerFactory = "jsaFactory")
    public Boolean sendAndReceiveClientExists(Long id) {
        Optional<Client> clientExists = clientService.findById(id);
        return clientExists.isPresent();
    }

}
