package com.block_chain.KLTN.event;

import com.block_chain.KLTN.helper.Mail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CustomSpringEventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(final Mail mail) {
        log.info("Start send email");
        CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, mail);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
