package com.block_chain.KLTN.domain.user;

import com.block_chain.KLTN.common.AppConstant;
import com.block_chain.KLTN.domain.auth.SignUpRequest;
import com.block_chain.KLTN.domain.auth.SignUpResponse;
import com.block_chain.KLTN.domain.user.role.RoleEntity;
import com.block_chain.KLTN.domain.user.role.RoleRepository;
import com.block_chain.KLTN.domain.verification.VerifyService;
import com.block_chain.KLTN.domain.wallet.CreateWalletEvent;
import com.block_chain.KLTN.domain.wallet.WalletQueryService;
import com.block_chain.KLTN.domain.wallet.WalletType;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import com.block_chain.KLTN.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserServiceImpl implements UserService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final VerifyService verifyService;
    private final WalletQueryService walletQueryService;

    @Transactional
    @Override
    public ResponseEntity<SignUpResponse> signUp(SignUpRequest request) {
        Optional<UserEntity> optUser = userRepository.findByEmail(request.email());
        List<RoleEntity> roles = roleRepository.findAll();
        if (optUser.isPresent()) {
            throw new BusinessException(ErrorMessage.RESOURCE_EXISTS, "user");
        }

        RoleEntity role = roles.stream()
                .filter(i -> AppConstant.Roles.USER.getRoleCode().equals(i.getRoleCode())).findFirst().get();
        
        UserEntity user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .status(UserStatus.INACTIVE)
                .build();
        user.addRole(role);
        userRepository.save(user);
        verifyService.createVerify(user);
        // applicationEventPublisher.publishEvent(new CreateWalletEvent(user.getId(), WalletType.USER));
        return ResponseEntity.ok(new SignUpResponse(user.getId()));
    }

    @Override
    public ResponseEntity<?> changePassword(UserPrincipal user, ChangePasswordRequest request) {
        UserEntity userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));

        if (!passwordEncoder.matches(request.oldPassword(), userEntity.getPassword())) {
            throw new BusinessException(ErrorMessage.OLD_PASSWORD_MISMATCH, userEntity.getEmail());
        }

        if (!passwordEncoder.matches(request.oldPassword(), request.confirmPassword())) {
            throw new BusinessException(ErrorMessage.CONFIRM_PASSWORD_MISMATCH);
        }
        userEntity.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(userEntity);
        return ResponseEntity.ok("Change password successfully");
    }

    @Override
    public String getUserAddress(String email) {
        Optional<UserEntity> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty()) {
            throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User");
        }
        UserEntity userEntity = optUser.get();
        return walletQueryService.getWallet(WalletType.USER, userEntity.getId()).getAddress();
    }
}
