package com.block_chain.KLTN.domain.post_offices;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostOfficesMapper {
    @Mapping(target = "locationTag", source = "locationTag")
    PostOfficesResponse toResponse(PostOfficesEntity postOfficesEntity);
}
