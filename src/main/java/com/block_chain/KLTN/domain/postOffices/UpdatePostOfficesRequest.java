package com.block_chain.KLTN.domain.postOffices;

import javax.validation.constraints.NotEmpty;

public record UpdatePostOfficesRequest(
    @NotEmpty String name,
    @NotEmpty String phone,
    @NotEmpty String address,
    @NotEmpty String sponsor,
    @NotEmpty String longitude,
    @NotEmpty String latitude,
    @NotEmpty String sponsorPhone
) {

}
