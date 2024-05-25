package com.block_chain.KLTN.domain.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.block_chain.KLTN.domain.employee.EmployeeEntity;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.verification.VerifyEntity;
import com.block_chain.KLTN.event.CustomSpringEventPublisher;
import com.block_chain.KLTN.helper.Mail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultMailService implements MailService{
    private final CustomSpringEventPublisher customSpringEventPublisher;

    @Value("${app.mail-from}")
    private String mailFrom;
    @Value("${app.email-prefix}")
    private String emailPrefix;

    private static final String MAIL_SUBJECT = "[%s] (%s)";
    private final String content = "Verify your account!";
    private final String employeeContent = "Welcome to Supply-blockchain!";

    public void sendVerifyMail(VerifyEntity verify, UserEntity user) {
        Mail mail = new Mail();
        mail.setSubject(String.format(MAIL_SUBJECT, emailPrefix, content));
        mail.setTemplate("VERIFICATION_MAIL");
        mail.addProp("code", verify.getCode());
        mail.addProp("name", user.getFullName());
        mail.setFrom(mailFrom);
        mail.setTo(user.getEmail());
        customSpringEventPublisher.publishCustomEvent(mail);
    }

    @Override
    public void sendEmployeeVerifyMail(EmployeeEntity employee, String password) {
        Mail mail = new Mail();
        mail.setSubject(String.format(MAIL_SUBJECT, emailPrefix, employeeContent));
        mail.setTemplate("EMPLOYEE_VERIFICATION_MAIL");
        mail.addProp("name", employee.getName());
        mail.addProp("email", employee.getEmail());
        mail.addProp("password", password);
        mail.setFrom(mailFrom);
        mail.setTo(employee.getEmail());
        customSpringEventPublisher.publishCustomEvent(mail);
    }
}
