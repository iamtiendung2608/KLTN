package com.block_chain.KLTN.domain.customer;

import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultCustomerService implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public DefaultCustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest customerRequest) {
        CustomerEntity entity = customerMapper.toEntity(customerRequest);
        return customerMapper.toResponse(customerRepository.save(entity));
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest customerRequest) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Customer"));
        customerMapper.updateEntity(entity, customerRequest);
        return customerMapper.toResponse(customerRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Customer"));
        entity.setDeleted(true);
        customerRepository.save(entity);
    }
}
