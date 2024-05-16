package com.block_chain.KLTN.domain.post_offices;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostOfficesMapper {
    @Mapping(target = "locationTag", source = "locationTag")
    PostOfficesResponse toResponse(PostOfficesEntity postOfficesEntity);

    PostOfficesEntity toEntity(PostOfficesRequest request);

    void updateEntity(@MappingTarget PostOfficesEntity postOfficeEntity, UpdatePostOfficesRequest request);
}
