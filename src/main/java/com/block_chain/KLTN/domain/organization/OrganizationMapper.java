package com.block_chain.KLTN.domain.organization;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    OrganizationEntity toEntity(CreateOrganizationRequest request);
}
