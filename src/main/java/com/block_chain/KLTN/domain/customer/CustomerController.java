package com.block_chain.KLTN.domain.customer;

import com.block_chain.KLTN.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerQueryService customerQueryService;
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerResponse getCustomerDetails(@PathVariable("id") Long id) {
        return customerQueryService.getCustomer(id);
    }

    @GetMapping("")
    public Page<CustomerResponse> searchCustomer(CustomerSearchRequest request, Pageable pageable,
                                                 @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal user) {
        return customerQueryService.searchCustomer(user, request, pageable);
    }

    @PostMapping("")
    public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest customerRequest) {
        return customerService.createCustomer(customerRequest);
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable("id") Long id, @Valid @RequestBody UpdateCustomerRequest customerRequest) {
        return customerService.updateCustomer(id, customerRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
