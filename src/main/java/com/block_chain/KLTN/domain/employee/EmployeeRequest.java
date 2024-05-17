package com.block_chain.KLTN.domain.employee;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EmployeeRequest (
        @NotBlank String name,
        @NotBlank String phone,
        @NotBlank String address,
        @NotBlank String email,
        @NotNull String password,
        @NotNull Long locationTagId,
        Set<Long> postOfficeIds
) {
}
