package com.block_chain.KLTN.domain.location_tag;

public record LocationTagSearchRequest(
        String keyword,
        Long locationId,
        SearchType type
) {
}
