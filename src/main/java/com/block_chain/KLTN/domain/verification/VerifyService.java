package com.block_chain.KLTN.domain.verification;

import com.block_chain.KLTN.domain.user.UserEntity;
import org.springframework.http.ResponseEntity;

public interface VerifyService {
    ResponseEntity<?> verify(Long id, VerifyRequest request);

    ResponseEntity<?> resendVerification(Long id);

    void createVerify(UserEntity user);
}
