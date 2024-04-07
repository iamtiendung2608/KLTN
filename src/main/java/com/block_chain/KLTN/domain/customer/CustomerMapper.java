package com.block_chain.KLTN.domain.customer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "location", source = "locationTag")
    CustomerResponse toResponse(CustomerEntity customer);
    CustomerEntity toEntity(CreateCustomerRequest request);
    void updateEntity(@MappingTarget CustomerEntity customer, UpdateCustomerRequest request);
}
