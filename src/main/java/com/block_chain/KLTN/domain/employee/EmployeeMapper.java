package com.block_chain.KLTN.domain.employee;

import com.block_chain.KLTN.domain.location_tag.LocationTagEntity;
import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    void updateEmployee(@MappingTarget EmployeeEntity existEmployee, EmployeeRequest request);

    @Mapping(target = "id", source = "employee.id")
    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "phone", source = "employee.phone")
    @Mapping(target = "address", source = "employee.address")
    @Mapping(target = "locationTag", source = "locationTag")
    EmployeeDetailResponse toDetailResponse(EmployeeEntity employee, LocationTagEntity locationTag);//, PostOfficesEntity postOffices);

    EmployeeResponse toResponse(EmployeeEntity employee);
}
