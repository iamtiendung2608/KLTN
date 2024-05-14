package com.block_chain.KLTN.domain.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeQueryService employeeQueryService;

    @PostMapping("")
    public CreateEmployeeResponse create(@Valid @RequestBody EmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Valid @RequestBody EmployeeRequest request) {
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
