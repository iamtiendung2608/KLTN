package com.block_chain.KLTN.domain.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @NotBlank
        String fullName
) {
}
