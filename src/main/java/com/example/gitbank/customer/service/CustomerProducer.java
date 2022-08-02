package com.example.gitbank.customer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.example.gitbank.config.RabbitMQTopicExchangeConfig.EXCHANGE_CUSTOMER_TOPIC;
import static com.example.gitbank.config.RabbitMQTopicExchangeConfig.ROUTING_KEY_CUSTOMER_CREATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerProducer {

    private final RabbitTemplate rabbitTemplate;

    public void createAccount(String id) {
        log.info("Trying to [{}] sent to exchange [{}] with routing key [{}]", id, EXCHANGE_CUSTOMER_TOPIC, ROUTING_KEY_CUSTOMER_CREATED);
        rabbitTemplate.convertAndSend(EXCHANGE_CUSTOMER_TOPIC, ROUTING_KEY_CUSTOMER_CREATED, id);
        log.info("Message [{}] send to exchange [{}] with routing key [{}]", id, EXCHANGE_CUSTOMER_TOPIC, ROUTING_KEY_CUSTOMER_CREATED);
    }
}
