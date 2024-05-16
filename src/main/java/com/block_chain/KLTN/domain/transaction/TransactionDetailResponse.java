package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.domain.postOffices.PostOfficesResponse;

public record TransactionDetailResponse(
        Long id,
        TransactionStatus status,
        String note,
        PostOfficesResponse postOffices
) {
}
