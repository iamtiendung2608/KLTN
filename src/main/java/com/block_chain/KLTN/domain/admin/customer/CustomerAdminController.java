package com.block_chain.KLTN.domain.admin.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.block_chain.KLTN.domain.customer.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/customer")
@RequiredArgsConstructor
public class CustomerAdminController{
    private final CustomerQueryService customerQueryService;

    @GetMapping("/")
    public Page<CustomerResponse> searchCustomer(CustomerAdminSearchRequest request, Pageable pageable) {
        return customerQueryService.searchCustomer(request, pageable);
    }
}
