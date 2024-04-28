package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.domain.post_offices.PostOfficesResponse;

public record TransactionDetailResponse(
        Long id,
        TransactionStatus status,
        String note,
        PostOfficesResponse postOffices
) {
}
