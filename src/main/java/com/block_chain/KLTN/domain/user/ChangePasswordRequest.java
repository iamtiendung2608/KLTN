package com.block_chain.KLTN.domain.user;

import javax.validation.constraints.NotBlank;

public record ChangePasswordRequest(
    @NotBlank
    String oldPassword,
    @NotBlank
    String newPassword,
    @NotBlank
    String confirmPassword
) {
}
