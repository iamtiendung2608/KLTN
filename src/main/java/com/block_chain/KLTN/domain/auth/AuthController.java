package com.block_chain.KLTN.domain.auth;

import com.block_chain.KLTN.domain.user.ChangePasswordRequest;
import com.block_chain.KLTN.domain.user.UserService;
import com.block_chain.KLTN.domain.verification.VerifyRequest;
import com.block_chain.KLTN.domain.verification.VerifyService;
import com.block_chain.KLTN.security.CustomAuthentication;
import com.block_chain.KLTN.security.TokenProvider;
import com.block_chain.KLTN.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final VerifyService verifyService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return userService.signUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new CustomAuthentication(request.email(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@Valid @RequestBody VerifyRequest request) {
        return verifyService.verify(request);
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerification(@Valid @RequestBody ResendOTPRequest request) {
        return verifyService.resendVerification(request);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request, @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal user) {
        return userService.changePassword(user, request);
    }
}
