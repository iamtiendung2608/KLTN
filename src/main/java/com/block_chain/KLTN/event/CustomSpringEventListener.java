package com.block_chain.KLTN.event;

import com.block_chain.KLTN.helper.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSpringEventListener {
    private final EmailSenderService emailSenderService;

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

}
