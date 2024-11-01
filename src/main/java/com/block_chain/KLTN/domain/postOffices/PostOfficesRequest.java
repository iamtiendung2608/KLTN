package com.block_chain.KLTN.domain.postOffices;

import javax.validation.constraints.NotEmpty;

public record PostOfficesRequest(
    @NotEmpty String name,
    @NotEmpty String phone,
    @NotEmpty String address,
    @NotEmpty String sponsor,
    @NotEmpty String code,
    @NotEmpty String sponsorPhone,
    @NotEmpty String longitude,
    @NotEmpty String latitude,
    @NotEmpty Long locationTagId
) {
    
}
