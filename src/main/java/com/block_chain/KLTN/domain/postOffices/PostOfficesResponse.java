package com.block_chain.KLTN.domain.postOffices;

import com.block_chain.KLTN.domain.location_tag.LocationTagResponse;

public record PostOfficesResponse(
        long id,
        String name,
        String phone,
        String address,
        String longitude,
        String latitude,
        String sponsor,
        String sponsorPhone,
        LocationTagResponse locationTag
) {
}
