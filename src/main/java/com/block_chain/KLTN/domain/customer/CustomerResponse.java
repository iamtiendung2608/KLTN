package com.block_chain.KLTN.domain.customer;

public record CustomerResponse(
    Long id,
    String fullName,
    String email,
    String phone,
    String address,
    LocationTagResponse location
) {
    public record LocationTagResponse(
        Long id,
        String province,
        String district,
        String ward
    ) {
    }
}
