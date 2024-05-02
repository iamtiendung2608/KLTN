package com.block_chain.KLTN.domain.location_tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationTagQueryService {
    Page<LocationTagResponse> getLocationTag(LocationTagSearchRequest request, Pageable pageable);

    LocationTagResponse getLocationTagById(Long id);
}
