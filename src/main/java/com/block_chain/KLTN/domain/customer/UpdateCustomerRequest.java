package com.block_chain.KLTN.domain.customer;

public record UpdateCustomerRequest(
    String fullName,
    String email,
    String phone,
    String address
) {
}
