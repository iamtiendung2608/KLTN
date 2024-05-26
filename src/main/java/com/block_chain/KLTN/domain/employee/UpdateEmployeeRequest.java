package com.block_chain.KLTN.domain.employee;

import javax.validation.constraints.NotBlank;

public record UpdateEmployeeRequest(
    @NotBlank String name,
    @NotBlank String phone,
    @NotBlank String address
) {

}
