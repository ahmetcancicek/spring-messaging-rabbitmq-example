package com.example.gitbank.customer.service;

import com.example.gitbank.customer.converter.CustomerConverter;
import com.example.gitbank.customer.messaging.CustomerNotification;
import com.example.gitbank.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import static com.example.gitbank.config.RabbitMQConfig.EXCHANGE_CUSTOMER_TOPIC;
import static com.example.gitbank.config.RabbitMQConfig.ROUTING_KEY_CUSTOMER_CREATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerNotificationServiceImpl implements CustomerNotificationService {

    private final CustomerConverter customerConverter;
    private final AmqpTemplate rabbitTemplate;

    @Override
    public void sendToQueue(Customer customer) {
        CustomerNotification customerNotification = customerConverter.fromCustomerToCustomerNotification(customer);
        log.info("Trying to [{}] send to exchange [{}] with routing key [{}]", customerNotification.toString(), EXCHANGE_CUSTOMER_TOPIC, ROUTING_KEY_CUSTOMER_CREATED);
        rabbitTemplate.convertAndSend(EXCHANGE_CUSTOMER_TOPIC, ROUTING_KEY_CUSTOMER_CREATED, customerNotification);
        log.info("Message [{}] sent to exchange [{}] with routing key [{}]", customerNotification.toString(), EXCHANGE_CUSTOMER_TOPIC, ROUTING_KEY_CUSTOMER_CREATED);
    }
}
