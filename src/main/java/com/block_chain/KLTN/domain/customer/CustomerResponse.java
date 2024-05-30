package com.block_chain.KLTN.domain.customer;

import com.block_chain.KLTN.domain.location_tag.LocationTagResponse;

import lombok.Getter;

public record CustomerResponse(
    Long id,
    String fullName,
    String email,
    String phone,
    String address,
    LocationTagResponse location
) {
}
