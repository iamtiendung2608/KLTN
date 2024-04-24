package com.block_chain.KLTN.domain.employee;

import com.block_chain.KLTN.domain.location_tag.LocationTagEntity;
import com.block_chain.KLTN.domain.location_tag.LocationTagRepository;
import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import com.block_chain.KLTN.domain.post_offices.PostOfficesRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmployeeQueryService implements EmployeeQueryService {
    private final EmployeeRepository employeeRepository;
    private final LocationTagRepository locationTagRepository;
    private final PostOfficesRepository postOfficesRepository;
    private final EmployeeMapper employeeMapper;

    public DefaultEmployeeQueryService(EmployeeRepository employeeRepository, LocationTagRepository locationTagRepository, PostOfficesRepository postOfficesRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.locationTagRepository = locationTagRepository;
        this.postOfficesRepository = postOfficesRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDetailResponse getEmployee(Long id) {
        EmployeeEntity existEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Employee"));
        LocationTagEntity locationTag = locationTagRepository.findById(existEmployee.getLocationTagId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "LocationTag"));
        // PostOfficesEntity postOffice = postOfficesRepository.findById(existEmployee.getPostOfficeId())
        //         .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Post Office"));

        return employeeMapper.toDetailResponse(existEmployee, locationTag);//, postOffice);
    }

    @Override
    public Page<EmployeeResponse> getEmployees(EmployeeSearchRequest request, Pageable pageable) {
        return employeeRepository.findAll(request.toPredicate(), pageable).map(employeeMapper::toResponse);
    }
}
