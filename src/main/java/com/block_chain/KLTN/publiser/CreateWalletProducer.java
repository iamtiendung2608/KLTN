package com.block_chain.KLTN.publiser;

import com.block_chain.KLTN.domain.wallet.WalletEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateWalletProducer {
    @Value("${rabbitmq.create-wallet-queue}")
    private String queue;

    private RabbitTemplate rabbitTemplate;

    public CreateWalletProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(WalletEntity walletEntity) {
        rabbitTemplate.convertAndSend(queue, walletEntity);
    }
}
