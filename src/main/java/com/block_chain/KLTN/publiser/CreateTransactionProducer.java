package com.block_chain.KLTN.publiser;

import org.springframework.stereotype.Service;

import com.block_chain.KLTN.domain.transactionEvent.TransactionEventEntity;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class CreateTransactionProducer {
    @Value("${rabbitmq.create-transaction-queue}")
    private String queue;

    private RabbitTemplate rabbitTemplate;

    public CreateTransactionProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(TransactionEventEntity request) {
        rabbitTemplate.convertAndSend(queue, request);
    }
}
