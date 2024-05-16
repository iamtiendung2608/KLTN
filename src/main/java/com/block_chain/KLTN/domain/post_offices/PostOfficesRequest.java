package com.block_chain.KLTN.domain.post_offices;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public record PostOfficesRequest(
    @NotEmpty String name,
    @NotEmpty String phone,
    @NotEmpty String address,
    @NotEmpty String sponsor,
    @NotEmpty String code,
    @NotEmpty String sponsorPhone,
    @NotEmpty Long locationTagId
) {
    
}
