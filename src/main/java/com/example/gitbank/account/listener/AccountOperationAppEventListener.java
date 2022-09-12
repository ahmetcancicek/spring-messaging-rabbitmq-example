package com.example.gitbank.account.listener;

import com.example.gitbank.account.event.AccountMoneyTransferEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.example.gitbank.config.RabbitMQConfig.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountOperationAppEventListener {

    private final AmqpTemplate rabbitTemplate;

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTransferMoney(AccountMoneyTransferEvent event) {
//        log.info("Trying to [{}] sent to exchange [{}] with routing key [{}]", event.toString(), EXCHANGE_ACCOUNT_DIRECT, ROUTING_KEY_ACCOUNT_TRANSFER);
//        rabbitTemplate.convertAndSend(EXCHANGE_ACCOUNT_DIRECT, ROUTING_KEY_ACCOUNT_TRANSFER, event);
//        log.info("Message [{}] send to exchange [{}] with routing key [{}]", event.toString(), EXCHANGE_ACCOUNT_DIRECT, ROUTING_KEY_ACCOUNT_TRANSFER);
    }
}
