package com.block_chain.KLTN.domain.verification;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.block_chain.KLTN.common.properties.VerificationProperties;
import com.block_chain.KLTN.domain.auth.ResendOTPRequest;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.domain.user.UserStatus;
import com.block_chain.KLTN.event.CustomSpringEventPublisher;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import com.block_chain.KLTN.helper.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;


@Service
@Slf4j
public class DefaultVerifyServiceImpl implements VerifyService {
    private final VerifyRepository verifyRepository;
    private final UserRepository userRepository;
    private final VerificationProperties verificationProperties;
    private final Random random;
    private static final String MAIL_SUBJECT = "[%s] (%s)";
    private final CustomSpringEventPublisher customSpringEventPublisher;


    @Value("${app.mail-from}")
    private String mailFrom;
    @Value("${app.email-prefix}")
    private String emailPrefix;
    private final String content = "Verify your account!";


    public DefaultVerifyServiceImpl(VerifyRepository verifyRepository, UserRepository userRepository, VerificationProperties verificationProperties, CustomSpringEventPublisher customSpringEventPublisher) {
        this.verifyRepository = verifyRepository;
        this.userRepository = userRepository;
        this.verificationProperties = verificationProperties;
        this.random = new SecureRandom();
        this.customSpringEventPublisher = customSpringEventPublisher;
    }

    @Transactional
    @Override
    public ResponseEntity<?> verify(VerifyRequest request) {
        VerifyEntity verification = verifyRepository.findByUserId(request.id())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Verification"));

        if (verification.isVerified()) {
            throw new BusinessException(ErrorMessage.USER_VERIFIED, "User");
        }

        if (!verification.getCode().equals(request.code())) {
            throw new BusinessException(ErrorMessage.INVALID_VERIFICATION_CODE);
        }

        verification.setVerified(true);
        verification.setVerifiedAt(OffsetDateTime.now());
        verifyRepository.save(verification);

        UserEntity user = userRepository.findById(verification.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));
        user.setStatus(UserStatus.ACTIVE);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @Override
    public ResponseEntity<?> resendVerification(ResendOTPRequest request) {
        VerifyEntity verification = verifyRepository.findByUserId(request.id())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Verification"));

        if (verification.isVerified()) {
            throw new BusinessException(ErrorMessage.USER_VERIFIED, "User");
        }

        final long waitingBetween = ChronoUnit.SECONDS.between(verification.getLastSendAt(), OffsetDateTime.now());
        if (waitingBetween < verificationProperties.getWaiting()) {
            throw new BusinessException(ErrorMessage.WAITING_UNDER_ALLOWED, "Next resend: %s".formatted(verification.getLastSendAt().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)));
        }

        verification.setCode(generateRandomCode());
        verification.setLastSendAt(OffsetDateTime.now());
        verifyRepository.save(verification);
        this.sendMail(verification, verification.getUser());
        return ResponseEntity.ok().build();
    }

    @Transactional
    @Override
    public void createVerify(UserEntity user) {
        VerifyEntity verification = VerifyEntity.builder()
                .userId(user.getId())
                .code(generateRandomCode())
                .lastSendAt(OffsetDateTime.now())
                .build();
        verifyRepository.save(verification);
        this.sendMail(verification, user);
    }

    private String generateRandomCode() {
        final char[] numeric = {'0','1','2','3','4','5','6','7','8','9' };
        return NanoIdUtils.randomNanoId(
                random,
                numeric,
                6
        );
    }

    private void sendMail(VerifyEntity verify, UserEntity user) {
        Mail mail = new Mail();
        mail.setSubject(String.format(MAIL_SUBJECT, emailPrefix, content));
        mail.setTemplate("VERIFICATION_MAIL");
        mail.addProp("code", verify.getCode());
        mail.setFrom(mailFrom);
        mail.setTo(user.getEmail());
        customSpringEventPublisher.publishCustomEvent(mail);
    }

}
