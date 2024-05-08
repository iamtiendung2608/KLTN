package com.block_chain.KLTN.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


    @Value("${rabbitmq.create-wallet-queue}")
    private String createWalletQueue;
    @Bean
    Queue queue() {
        return new Queue(createWalletQueue, Boolean.FALSE);
    }

    @Value("${rabbitmq.create-transaction-queue}")
    private String createTransactionQueue;
    @Bean
    Queue transactionQueue() {
        return new Queue(createTransactionQueue, Boolean.FALSE);
    }
}
