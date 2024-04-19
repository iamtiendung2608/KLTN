package com.block_chain.KLTN.domain.customer;

import javax.validation.constraints.NotNull;

public record CreateCustomerRequest(
    @NotNull String fullName,
    @NotNull String email,
    @NotNull String phone,
    String address,
    @NotNull Long locationTagId
) {
}
