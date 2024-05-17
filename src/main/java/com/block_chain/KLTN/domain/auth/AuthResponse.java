package com.block_chain.KLTN.domain.auth;

public record AuthResponse(
    String accessToken,
    String roleCode
) {
}
