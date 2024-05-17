package com.block_chain.KLTN.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;


    public void sendEmail(Mail mail) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(mail.getProps());

        String html = templateEngine.process(mail.getTemplate(), context);
        helper.setTo(Objects.requireNonNull(mail.getTo()));
        if (mail.getCc() != null) {
            helper.setCc(mail.getCc());
        }
        if (mail.getBcc() != null) {
            helper.setBcc(mail.getBcc());
        }
        helper.setText(html, true);
        helper.setSubject(Objects.requireNonNull(mail.getSubject()));
        helper.setFrom(Objects.requireNonNull(mail.getFrom()));
        if (mail.getFiles() != null && !mail.getFiles().isEmpty()) {
            for (File file : mail.getFiles()) {
                String originalName = file.getName();
                helper.addAttachment(originalName, file);
            }
        }

        emailSender.send(message);
    }
}
