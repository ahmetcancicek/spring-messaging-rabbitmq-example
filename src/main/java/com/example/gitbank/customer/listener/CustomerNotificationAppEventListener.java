package com.example.gitbank.customer.listener;

import com.example.gitbank.customer.event.CustomerCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.example.gitbank.config.RabbitMQConfig.EXCHANGE_CUSTOMER_TOPIC;
import static com.example.gitbank.config.RabbitMQConfig.ROUTING_KEY_CUSTOMER_CREATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerNotificationAppEventListener {

    private final AmqpTemplate rabbitTemplate;

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSendToNotification(CustomerCreatedEvent event) {
        log.info("Trying to [{}] sent to exchange [{}] with routing key [{}]", event.toString(), EXCHANGE_CUSTOMER_TOPIC, ROUTING_KEY_CUSTOMER_CREATED);
        rabbitTemplate.convertAndSend(EXCHANGE_CUSTOMER_TOPIC, ROUTING_KEY_CUSTOMER_CREATED, event);
        log.info("Message [{}] send to exchange [{}] with routing key [{}]", event.toString(), EXCHANGE_CUSTOMER_TOPIC, ROUTING_KEY_CUSTOMER_CREATED);
    }
}
