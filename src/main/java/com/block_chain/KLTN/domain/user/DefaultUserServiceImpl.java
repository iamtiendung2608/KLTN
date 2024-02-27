package com.block_chain.KLTN.domain.user;

import com.block_chain.KLTN.common.AppConstant;
import com.block_chain.KLTN.domain.auth.SignUpRequest;
import com.block_chain.KLTN.domain.user.role.RoleEntity;
import com.block_chain.KLTN.domain.user.role.RoleRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<Void> signUp(SignUpRequest request) {
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
                .status(UserStatus.ACTIVE)
                .build();
        user.addRole(role);
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }
}
