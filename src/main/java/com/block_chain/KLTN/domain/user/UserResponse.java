package com.block_chain.KLTN.domain.user;

public record UserResponse(
    Long id,
    String email,
    Long organizationId,
    UserStatus status,
    String fullName
) {
    
}
