package com.block_chain.KLTN.domain.user;

import com.block_chain.KLTN.domain.auth.SignUpRequest;
import com.block_chain.KLTN.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<Long> signUp(SignUpRequest request);

    ResponseEntity<?> changePassword(UserPrincipal user, ChangePasswordRequest request);
}
