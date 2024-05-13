package com.block_chain.KLTN.domain.customer;

import com.block_chain.KLTN.domain.admin.customer.CustomerAdminSearchRequest;
import com.block_chain.KLTN.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerQueryService {
    CustomerResponse getCustomer(Long id);

    Page<CustomerResponse> searchCustomer(UserPrincipal user, CustomerSearchRequest request, Pageable pageable);
    Page<CustomerResponse> searchCustomer(CustomerAdminSearchRequest request, Pageable pageable);

}
