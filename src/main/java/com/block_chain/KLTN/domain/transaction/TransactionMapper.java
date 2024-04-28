package com.block_chain.KLTN.domain.transaction;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDetailResponse toDetailResponse(TransactionEntity transaction);

    TransactionResponse toResponse(TransactionEntity transactionEntity);
}
