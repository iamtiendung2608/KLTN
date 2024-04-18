package com.block_chain.KLTN.domain.organization;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    @Mapping(target = "id", ignore = true)
    OrganizationEntity toEntity(CreateOrganizationRequest request);
}
