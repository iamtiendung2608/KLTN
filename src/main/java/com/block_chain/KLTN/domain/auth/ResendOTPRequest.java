package com.block_chain.KLTN.domain.auth;

import javax.validation.constraints.NotNull;

public record ResendOTPRequest(
        @NotNull Long id
) {
}
