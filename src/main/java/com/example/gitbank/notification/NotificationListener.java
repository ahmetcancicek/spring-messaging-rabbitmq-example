package com.example.gitbank.notification;

import com.example.gitbank.notification.messaging.Account;
import com.example.gitbank.notification.messaging.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.example.gitbank.config.RabbitMQConfig.QUEUE_ACCOUNT_CREATED;
import static com.example.gitbank.config.RabbitMQConfig.QUEUE_CUSTOMER_CREATED;

@Slf4j
@Service
public class NotificationListener {

    @RabbitListener(queues = {QUEUE_CUSTOMER_CREATED})
    public void listenOnQueueCustomerCreated(Customer customer) {
        log.info("Listener on queue [{}] received message [{}]", QUEUE_CUSTOMER_CREATED, customer.toString());
    }

    @RabbitListener(queues = {QUEUE_ACCOUNT_CREATED})
    public void listenOnQueueAccountCreated(Account account) {
        log.info("Listener on queue [{}] received message [{}]", QUEUE_ACCOUNT_CREATED, account.toString());
    }
}
