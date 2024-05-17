package com.block_chain.KLTN.domain.transfer_object;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferObjectMapper {
    TransferObjectResponse toResponse(TransferObjectEntity transferObjectEntity);
}
