package com.block_chain.KLTN.domain.customer;

import com.block_chain.KLTN.domain.admin.customer.CustomerAdminSearchRequest;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import com.block_chain.KLTN.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class DefaultCustomerQueryService implements CustomerQueryService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserRepository userRepository;
    @PersistenceContext
    EntityManager entityManager;

    public DefaultCustomerQueryService(CustomerRepository customerRepository, CustomerMapper customerMapper, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.userRepository = userRepository;
    }

    @Override
    public CustomerResponse getCustomer(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Customer"));
        return customerMapper.toResponse(entity);
    }

    @Override
    public Page<CustomerResponse> searchCustomer(UserPrincipal user, CustomerSearchRequest request, Pageable pageable) {
        UserEntity userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));
        return customerRepository.findAll(request.toPredicate(userEntity.getOrganizationId()), pageable).map(customerMapper::toResponse);
    }

    @Override
    public Page<CustomerResponse> searchCustomer(CustomerAdminSearchRequest request, Pageable pageable) {
        return customerRepository.findAll(request.toPredicate(), pageable).map(customerMapper::toResponse);
    }
}
