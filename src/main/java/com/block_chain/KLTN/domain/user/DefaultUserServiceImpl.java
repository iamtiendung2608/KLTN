package com.block_chain.KLTN.domain.user;

import com.block_chain.KLTN.common.AppConstant;
import com.block_chain.KLTN.domain.auth.SignUpRequest;
import com.block_chain.KLTN.domain.auth.SignUpResponse;
import com.block_chain.KLTN.domain.user.role.RoleEntity;
import com.block_chain.KLTN.domain.user.role.RoleRepository;
import com.block_chain.KLTN.domain.verification.VerifyService;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import com.block_chain.KLTN.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultUserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final VerifyService verifyService;
    private final UserMapper userMapper;

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
                .fullName(request.fullName())
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

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new BusinessException(ErrorMessage.CONFIRM_PASSWORD_MISMATCH);
        }
        userEntity.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(userEntity);
        return ResponseEntity.ok("Change password successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public String getUserRole(String email) {
        Optional<UserEntity> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty()) {
            throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User");
        }
        return optUser.get().getUserRoles().stream().findFirst().get().getRoleCode();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> getUser(long id) {
        return userRepository.findById(id).map(userMapper::toResponse);
    }

    @Override
    @Transactional
    public void updateUser(Long id, UpdateUserRequest request) {
        Optional<UserEntity> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User");
        }
        UserEntity user = optUser.get();
        user.setFullName(request.fullName());
        userRepository.save(user);
    }
}
