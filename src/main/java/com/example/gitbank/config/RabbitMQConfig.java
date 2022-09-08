package com.example.gitbank.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_CUSTOMER_TOPIC = "x.customer";
    public static final String ROUTING_KEY_CUSTOMER_CREATED = "customer.event.created";
    public static final String QUEUE_CUSTOMER_CREATED = "q.customer-created-queue";


    public static final String EXCHANGE_ACCOUNT_DIRECT = "x.account";
    public static final String ROUTING_KEY_ACCOUNT_CREATED = "account.event.created";
    public static final String QUEUE_ACCOUNT_CREATED = "q.account-created-queue";

    @Bean
    public TopicExchange exchangeCustomerTopic() {
        return new TopicExchange(EXCHANGE_CUSTOMER_TOPIC);
    }
    @Bean
    public Queue queueCustomerCreated() {
        return new Queue(QUEUE_CUSTOMER_CREATED);
    }

    @Bean
    public Declarables topicExchangeBindings(TopicExchange exchangeCustomerTopic,
                                             Queue queueCustomerCreated) {
        return new Declarables(
            BindingBuilder.bind(queueCustomerCreated).to(exchangeCustomerTopic).with(ROUTING_KEY_CUSTOMER_CREATED)
        );
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
