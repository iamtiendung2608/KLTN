package com.block_chain.KLTN.domain.location_tag;

import java.util.List;

public interface LocationTagQueryService {
    List<LocationTagResponse> getLocationTag(LocationTagSearchRequest request);

    LocationTagResponse getLocationTagById(Long id);
}
