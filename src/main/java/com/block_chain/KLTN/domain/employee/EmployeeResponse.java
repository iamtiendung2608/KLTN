package com.block_chain.KLTN.domain.employee;

import java.time.OffsetDateTime;

public record EmployeeResponse(
        Long id,
        String name,
        String phone,
        String address,
        String email,
        Boolean active,
        OffsetDateTime createdAt
) {
}
