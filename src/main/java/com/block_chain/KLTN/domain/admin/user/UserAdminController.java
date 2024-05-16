package com.block_chain.KLTN.domain.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.block_chain.KLTN.domain.user.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserQueryService userQueryService;

    @GetMapping("/")
    public Page<UserResponse> searchUser(UserAdminSearchRequest request, Pageable pageable) {
        return userQueryService.searchUser(request, pageable);
    }
    
}
