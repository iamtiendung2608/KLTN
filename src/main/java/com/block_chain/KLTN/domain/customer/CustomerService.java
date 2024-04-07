package com.block_chain.KLTN.domain.customer;

public interface CustomerService {
    CustomerResponse createCustomer(CreateCustomerRequest customerRequest);

    CustomerResponse updateCustomer(Long id, UpdateCustomerRequest customerRequest);

    void deleteCustomer(Long id);
}
