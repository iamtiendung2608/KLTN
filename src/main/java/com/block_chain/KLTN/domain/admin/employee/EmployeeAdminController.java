package com.block_chain.KLTN.domain.admin.employee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.block_chain.KLTN.domain.employee.*;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/employee")
public class EmployeeAdminController {
    private final EmployeeService employeeService;
    private final EmployeeQueryService employeeQueryService;

    @PostMapping("")
    public CreateEmployeeResponse createEmployee(@Valid @RequestBody EmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody UpdateEmployeeRequest request) {
        employeeService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable("id") Long id, @Valid @RequestBody UpdateEmployeeStatus request) {
        employeeService.updateStatus(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public EmployeeDetailResponse getEmployee(@PathVariable("id") Long id) {
        return employeeQueryService.getEmployee(id);
    }

    @GetMapping("")
    public Page<EmployeeResponse> getEmployees(EmployeeSearchRequest request, Pageable pageable) {
        return employeeQueryService.getEmployees(request, pageable);
    }

    @PutMapping("/{id}/post-offices")
    public ResponseEntity<Void> updateLocation(@PathVariable("id") Long id, @Valid @RequestBody UpdateEmployeeLocationRequest request) {
        employeeService.updateLocation(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
