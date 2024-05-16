package com.block_chain.KLTN.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.block_chain.KLTN.domain.admin.user.UserAdminSearchRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class DefaultUserQueryService implements UserQueryService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserResponse> searchUser(UserAdminSearchRequest request, Pageable pageable) {
        return userRepository.findAll(request.toPredicate(), pageable).map(userMapper::toResponse);
    }
    
}
