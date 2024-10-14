package com.crisda24.neoris.transactionaccount.infrastructure.rabbit;

import com.crisda24.neoris.transactionaccount.application.services.account.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    private final AccountService accountService;

    private static final String ACCOUNT_EXISTS = "queue.account.exists";

    public ConsumerService(AccountService accountService) {
        this.accountService = accountService;
    }

    @RabbitListener(queues = ACCOUNT_EXISTS, containerFactory = "jsaFactory")
    public Boolean sendAndReceiveAccountExists(Long id) {
        Integer totalAccountsByClient = accountService.totalAccountsByClient(id);
        if(totalAccountsByClient <0){
            return null;
        }
        return totalAccountsByClient > 0;
    }
}
