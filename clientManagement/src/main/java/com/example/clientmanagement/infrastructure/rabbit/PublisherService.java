package com.example.clientmanagement.infrastructure.rabbit;

import com.example.clientmanagement.domain.exception.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    private final Logger logger = LoggerFactory.getLogger(PublisherService.class);
    private final AmqpTemplate template;
    private static final String ACCOUNT_EXCHANGE = "exchange.account.client";
    private static final String ROUTING_ACCOUNT_CLIENT = "routing.account.exists";

    public PublisherService(AmqpTemplate template) {
        this.template = template;
    }

    public Boolean sendAndReceiveAccountExists(Long id) {

        Boolean response;
        try {
            response = (Boolean)  template.convertSendAndReceive(ACCOUNT_EXCHANGE, ROUTING_ACCOUNT_CLIENT,
                    id, message -> {
                        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
                        return message;
                    });
        } catch (Exception e) {
            logger.error("sendAndReceiveAccountExists - Error processing response {}", e.getMessage());
            throw  new GeneralException("An unexpected error has occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
