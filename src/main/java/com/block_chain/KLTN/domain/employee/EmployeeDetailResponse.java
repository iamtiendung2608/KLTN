package com.block_chain.KLTN.domain.employee;

import com.block_chain.KLTN.domain.location_tag.LocationTagResponse;
import com.block_chain.KLTN.domain.post_offices.PostOfficesResponse;

import java.time.OffsetDateTime;

public record EmployeeDetailResponse(
        Long id,
        String name,
        String phone,
        String address,
        String email,
        Boolean active,
        OffsetDateTime createdAt,
        LocationTagResponse locationTag
        // PostOfficesResponse postOffices
) {
}
