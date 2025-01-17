package com.crisda24.neoris.transactionaccount.infrastructure.input.adapter;

import com.crisda24.neoris.transactionaccount.application.input.port.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerAdapter {
    private final Logger logger = LoggerFactory.getLogger(ConsumerAdapter.class);
    private final AccountService accountService;

    private static final String ACCOUNT_EXISTS = "queue.account.exists";

    public ConsumerAdapter(AccountService accountService) {
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
