package com.block_chain.KLTN.domain.employee;

import java.util.Set;

public record UpdateEmployeeLocationRequest(
        Set<Long> postOfficeIds
) {
}
