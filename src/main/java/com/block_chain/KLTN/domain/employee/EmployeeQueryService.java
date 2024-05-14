package com.block_chain.KLTN.domain.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeQueryService {
    EmployeeDetailResponse getEmployee(Long id);

    Page<EmployeeResponse> getEmployees(EmployeeSearchRequest request, Pageable pageable);
}
