package com.example.gitbank.account.service;

import com.example.gitbank.account.mapper.AccountConverter;
import com.example.gitbank.account.messaging.AccountNotification;
import com.example.gitbank.account.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import static com.example.gitbank.config.RabbitMQConfig.EXCHANGE_ACCOUNT_DIRECT;
import static com.example.gitbank.config.RabbitMQConfig.ROUTING_KEY_ACCOUNT_CREATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountNotificationServiceImpl implements AccountNotificationService {

    private final AccountConverter accountConverter;
    private final AmqpTemplate rabbitTemplate;

    @Override
    public void sendNotificationForCreatedAccount(Account account) {
        AccountNotification accountNotification = accountConverter.fromAccountToAccountNotification(account);
        log.info("Trying to [{}] send to exchange [{}] with routing key [{}]", accountNotification.toString(), EXCHANGE_ACCOUNT_DIRECT, ROUTING_KEY_ACCOUNT_CREATED);
        rabbitTemplate.convertAndSend(EXCHANGE_ACCOUNT_DIRECT, ROUTING_KEY_ACCOUNT_CREATED, accountNotification);
        log.info("Message [{}] sent to exchange [{}] with routing key [{}]", accountNotification.toString(), EXCHANGE_ACCOUNT_DIRECT, ROUTING_KEY_ACCOUNT_CREATED);
    }
}
