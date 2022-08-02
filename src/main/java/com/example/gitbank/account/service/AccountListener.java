package com.example.gitbank.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.example.gitbank.config.RabbitMQConfig.QUEUE_CUSTOMER_CREATED;

@Slf4j
@Service
public class AccountListener {

    @RabbitListener(queues = {QUEUE_CUSTOMER_CREATED})
    public void listenOnQueueCustomerCreated(String id) {
        log.info("Listener on queue [{}] received message [{}]", QUEUE_CUSTOMER_CREATED, id);
    }
}
