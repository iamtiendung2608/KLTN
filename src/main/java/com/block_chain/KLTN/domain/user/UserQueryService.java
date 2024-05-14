package com.block_chain.KLTN.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.block_chain.KLTN.domain.admin.user.UserAdminSearchRequest;

public interface UserQueryService {
    Page<UserResponse> searchUser(UserAdminSearchRequest request, Pageable pageable);
}
