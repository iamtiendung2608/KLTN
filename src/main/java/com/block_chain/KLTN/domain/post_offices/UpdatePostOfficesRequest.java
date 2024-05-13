package com.block_chain.KLTN.domain.post_offices;

import javax.validation.constraints.NotEmpty;

public record UpdatePostOfficesRequest(
    @NotEmpty String name,
    @NotEmpty String phone,
    @NotEmpty String address,
    @NotEmpty String sponsor,
    @NotEmpty String code,
    @NotEmpty String sponsorPhone
) {

}
