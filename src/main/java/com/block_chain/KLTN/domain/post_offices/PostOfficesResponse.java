package com.block_chain.KLTN.domain.post_offices;

public record PostOfficesResponse(
    long id,
    String name,
    String phone,
    String address,
    String longitude,
    String latitude,
    String sponsor,
    String sponsorPhone
) {
}
