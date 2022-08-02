package com.example.gitbank.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopicExchangeConfig {

    public static  String EXCHANGE_CUSTOMER_TOPIC = "x.customer";
    public static final String ROUTING_KEY_CUSTOMER_CREATED = "x.customer.created";

    @Bean
    public TopicExchange exchangeCustomerTopic() {
        return new TopicExchange(EXCHANGE_CUSTOMER_TOPIC);
    }

    @Bean
    public Declarables topicExchangeBindings(TopicExchange exchangeCustomerTopic,
                                             Queue queueCustomerCreated) {
        return new Declarables(
                BindingBuilder.bind(queueCustomerCreated).to(exchangeCustomerTopic).with(ROUTING_KEY_CUSTOMER_CREATED)
        );
    }

}
