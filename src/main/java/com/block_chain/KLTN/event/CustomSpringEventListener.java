package com.block_chain.KLTN.event;

import com.block_chain.KLTN.domain.transactionEvent.CreateTransactionEvent;
import com.block_chain.KLTN.domain.transactionEvent.TransactionEventService;
import com.block_chain.KLTN.domain.wallet.CreateWalletEvent;
import com.block_chain.KLTN.domain.wallet.WalletService;
import com.block_chain.KLTN.helper.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.mail.MessagingException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSpringEventListener {
    private final EmailSenderService emailSenderService;
    private final WalletService walletService;
    private final TransactionEventService transactionEventService;

    @Async
    @EventListener(classes = CustomSpringEvent.class)
    public void onApplicationEvent(CustomSpringEvent event) {
        try {
            emailSenderService.sendEmail(event.getMail());
            log.info("Send email successful.");
        } catch (MessagingException e) {
            log.error("Send mail failed", e);
        }
    }

    @Async
    @TransactionalEventListener
    public void handleTransactionProcessedEvent(CreateWalletEvent event) {
        log.debug("Received create wallet event: " + event);
        walletService.createWallet(event);
    }

    @Async
    @TransactionalEventListener
    public void handleTransactionProcessedEvent(CreateTransactionEvent event) {
        log.debug("Received create wallet event: " + event);
        transactionEventService.createTransactionEvent(event.oldTransaction(), event.newTransaction());
    }

}
