package com.block_chain.KLTN.event;

import com.block_chain.KLTN.helper.Mail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class CustomSpringEvent extends ApplicationEvent {
    private Mail mail;

    public CustomSpringEvent(Object source, Mail mail) {
        super(source);
        this.mail = mail;
    }
}
