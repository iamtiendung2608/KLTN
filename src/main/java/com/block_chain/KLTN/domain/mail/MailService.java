package com.block_chain.KLTN.domain.mail;

import com.block_chain.KLTN.domain.employee.EmployeeEntity;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.verification.VerifyEntity;

public interface MailService {
    void sendVerifyMail(VerifyEntity verify, UserEntity user);
    void sendEmployeeVerifyMail(EmployeeEntity employee, String password);
}
