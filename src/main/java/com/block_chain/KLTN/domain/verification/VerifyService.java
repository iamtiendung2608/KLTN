package com.block_chain.KLTN.domain.verification;

import com.block_chain.KLTN.domain.auth.ResendOTPRequest;
import com.block_chain.KLTN.domain.user.UserEntity;
import org.springframework.http.ResponseEntity;

public interface VerifyService {
    ResponseEntity<?> verify(VerifyRequest request);

    ResponseEntity<?> resendVerification(ResendOTPRequest id);

    void createVerify(UserEntity user);
}
