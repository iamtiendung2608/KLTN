package com.block_chain.KLTN.domain.user;

import com.block_chain.KLTN.domain.auth.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<Void> signUp(SignUpRequest request);
}
