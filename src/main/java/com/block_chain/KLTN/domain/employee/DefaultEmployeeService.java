package com.block_chain.KLTN.domain.employee;

import com.block_chain.KLTN.domain.location_tag.LocationTagEntity;
import com.block_chain.KLTN.domain.location_tag.LocationTagRepository;
import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import com.block_chain.KLTN.domain.post_offices.PostOfficesRepository;
import com.block_chain.KLTN.domain.wallet.CreateWalletEvent;
import com.block_chain.KLTN.domain.wallet.WalletType;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultEmployeeService implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final LocationTagRepository locationTagRepository;
    private final PostOfficesRepository postOfficesRepository;
    private final EmployeeMapper employeeMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public CreateEmployeeResponse create(EmployeeRequest request) {
        Optional<EmployeeEntity> existUser = employeeRepository.findByEmail(request.email());
        LocationTagEntity locationTag = locationTagRepository.findById(request.locationTagId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "LocationTag"));
        List<PostOfficesEntity> postOfficesEntityList = postOfficesRepository.findAllById(
            request.postOfficeIds());

        if (postOfficesEntityList.size() != request.postOfficeIds().size()) {
            throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Post Office");
        }

        if (existUser.isPresent()) {
            throw new BusinessException(ErrorMessage.RESOURCE_EXISTS, "User");
        }

        EmployeeEntity employee = EmployeeEntity.builder()
                .name(request.name())
                .phone(request.phone())
                .address(request.address())
                .email(request.email())
                .active(true)
                .postOffices(new HashSet<>(postOfficesEntityList))
                .locationTagId(locationTag.getId())
                .build();
        employeeRepository.save(employee);
        applicationEventPublisher.publishEvent(new CreateWalletEvent(Long.toString(employee.getId()), WalletType.EMPLOYEE));
        //TODO: create new user principal for each employee

        return new CreateEmployeeResponse(employee.getId());
    }

    @Override
    @Transactional
    public void update(Long id, EmployeeRequest request) {
        EmployeeEntity existEmployee = employeeRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Employee"));
        LocationTagEntity locationTag = locationTagRepository.findById(request.locationTagId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "LocationTag"));
        List<PostOfficesEntity> postOfficesEntityList = postOfficesRepository.findAllById(
                request.postOfficeIds());

        if (postOfficesEntityList.size() != request.postOfficeIds().size()) {
            throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Post Office");
        }

        employeeMapper.updateEmployee(existEmployee, request);
        existEmployee.setLocationTagId(locationTag.getId());
        existEmployee.getPostOffices().addAll(new HashSet<>(postOfficesEntityList));
        existEmployee.getPostOffices().removeIf(postOfficesEntity -> !request.postOfficeIds().contains(postOfficesEntity.getId()));
        employeeRepository.save(existEmployee);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, UpdateEmployeeStatus request) {
        EmployeeEntity existEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Employee"));
        existEmployee.setActive(request.active());
        employeeRepository.save(existEmployee);
    }

    @Override
    @Transactional
    public void updateLocation(Long id, UpdateEmployeeLocationRequest request) {
        EmployeeEntity existEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Employee"));
        List<PostOfficesEntity> postOfficesEntityList = postOfficesRepository.findAllById(
                request.postOfficeIds());

        if (postOfficesEntityList.size() != request.postOfficeIds().size()) {
            throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Post Office");
        }
        existEmployee.getPostOffices().addAll(new HashSet<>(postOfficesEntityList));
        existEmployee.getPostOffices().removeIf(postOfficesEntity -> !request.postOfficeIds().contains(postOfficesEntity.getId()));
        employeeRepository.save(existEmployee);
    }
}
