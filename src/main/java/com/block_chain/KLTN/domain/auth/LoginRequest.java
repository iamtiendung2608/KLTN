package com.block_chain.KLTN.domain.auth;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank
    String email,
    @NotBlank
    String password
) {
}
