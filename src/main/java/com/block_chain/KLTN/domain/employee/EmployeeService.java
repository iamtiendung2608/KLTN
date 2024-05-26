package com.block_chain.KLTN.domain.employee;

public interface EmployeeService {
    CreateEmployeeResponse create(EmployeeRequest request);

    void update(Long id, UpdateEmployeeRequest request);

    void updateStatus(Long id, UpdateEmployeeStatus request);

    void updateLocation(Long id, UpdateEmployeeLocationRequest request);
}
