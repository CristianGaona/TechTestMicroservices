package com.crisda24.neoris.transactionaccount.infrastructure.output.adapter;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.client.ClientInfoResponseDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.account.messages.ClientIdListMessage;
import com.crisda24.neoris.transactionaccount.infrastructure.input.adapter.exceptions.GeneralException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherAdapter {

    private final Logger logger = LoggerFactory.getLogger(PublisherAdapter.class);
    private final AmqpTemplate template;
    private final ObjectMapper mapper;


    private static final String ACCOUNT_EXCHANGE = "exchange.account.client";
    private static final String ROUTING_ACCOUNT_CLIENT_INFO = "routing.account.client.info";
    private static final String ROUTING_CLIENT_EXISTS = "routing.client.exists";


    public PublisherAdapter(AmqpTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public List<ClientInfoResponseDto> sendAndReceiveClientsInfo(List<Long> ids) {
        String parsedMessage;
        ClientIdListMessage clientIdListMessage = new ClientIdListMessage();
        clientIdListMessage.setIds(ids);
        try {
            parsedMessage = mapper.writeValueAsString(clientIdListMessage);
        } catch (Exception e) {
            logger.error("sendAndReceiveClientsInfo - Error parsing input data: {}", e.getMessage());
            throw  new GeneralException("An unexpected error has occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String response;
        try {
            response = (String) template.convertSendAndReceive(ACCOUNT_EXCHANGE, ROUTING_ACCOUNT_CLIENT_INFO,
                    parsedMessage, new MessagePostProcessor() {
                        public Message postProcessMessage(Message message) throws AmqpException {
                            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
                            return message;
                        }
                    });
        } catch (Exception e) {
            logger.error("sendAndReceiveClientsInfo - Error processing response {}", e.getMessage());
            throw  new GeneralException("An unexpected error has occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

       try {
            return mapper.readValue(response, new TypeReference<>() {
            });
        } catch (Exception e) {
            logger.error("sendAndReceiveClientsInfo - Response parse error: {}", e.getMessage());
            throw  new GeneralException("An unexpected error has occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    public Boolean sendAndReceiveClientExists(Long id) {
        Boolean response;
        try {
            response = (Boolean)  template.convertSendAndReceive(ACCOUNT_EXCHANGE, ROUTING_CLIENT_EXISTS,
                    id, message -> {
                        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
                        return message;
                    });
        } catch (Exception e) {
            logger.error("sendAndReceiveClientExists - Error processing response {}", e.getMessage());
            throw  new GeneralException("An unexpected error has occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
