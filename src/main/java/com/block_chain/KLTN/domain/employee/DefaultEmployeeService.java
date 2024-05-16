package com.block_chain.KLTN.domain.employee;

import com.block_chain.KLTN.common.AppConstant;
import com.block_chain.KLTN.domain.location_tag.LocationTagEntity;
import com.block_chain.KLTN.domain.location_tag.LocationTagRepository;
import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import com.block_chain.KLTN.domain.post_offices.PostOfficesRepository;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.domain.user.UserStatus;
import com.block_chain.KLTN.domain.user.role.RoleEntity;
import com.block_chain.KLTN.domain.user.role.RoleRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public CreateEmployeeResponse create(EmployeeRequest request) {
        Optional<EmployeeEntity> existUser = employeeRepository.findByEmail(request.email());
        LocationTagEntity locationTag = locationTagRepository.findById(request.locationTagId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "LocationTag"));
        List<PostOfficesEntity> postOfficesEntityList = postOfficesRepository.findAllById(
            request.postOfficeIds());

        
        List<RoleEntity> roles = roleRepository.findAll();
        RoleEntity role = roles.stream()
            .filter(i -> AppConstant.Roles.EMPLOYEE.getRoleCode().equals(i.getRoleCode())).findFirst().get();
    
        if (postOfficesEntityList.size() != request.postOfficeIds().size()) {
            throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Post Office");
        }


        if (existUser.isPresent() || userRepository.existsByEmail(request.email())) {
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
        
        UserEntity user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .status(UserStatus.ACTIVE)
                .build();
        user.addRole(role);
        employeeRepository.save(employee);
        userRepository.save(user);
        //TODO: create new user principal for each employee - Solved

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
