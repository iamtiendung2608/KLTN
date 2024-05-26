package com.block_chain.KLTN.domain.employee;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EmployeeRequest (
        @NotBlank String name,
        @NotBlank String phone,
        @NotBlank String address,
        @NotBlank String email,
        @NotNull Long locationTagId
) {
}
