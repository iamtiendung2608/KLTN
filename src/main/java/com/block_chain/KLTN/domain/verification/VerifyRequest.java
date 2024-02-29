package com.block_chain.KLTN.domain.verification;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record VerifyRequest(
    @NotBlank
    @Size(min = 6, max = 6)
    String code
) {
}
