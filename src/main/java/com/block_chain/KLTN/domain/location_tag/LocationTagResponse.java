package com.block_chain.KLTN.domain.location_tag;

public record LocationTagResponse(
        Long id,
        String province,
        String district,
        String ward
) {
}
