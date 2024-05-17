package com.block_chain.KLTN.domain.customer;

import com.block_chain.KLTN.domain.location_tag.LocationTagEntity;
import com.block_chain.KLTN.domain.location_tag.LocationTagRepository;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultCustomerService implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final LocationTagRepository locationTagRepository;

    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest customerRequest) {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByEmail(userDetail.getUsername())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));

        CustomerEntity entity = customerMapper.toEntity(customerRequest);
        LocationTagEntity locationTag = locationTagRepository.findById(customerRequest.locationTagId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "LocationTag"));
        
        entity.setLocationTagId(locationTag.getId());
        entity.setOrganizationId(user.getOrganizationId());
        
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
