package com.block_chain.KLTN.domain.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionQueryService {
    TransactionDetailResponse getTransactionDetail(Long id);

    Page<TransactionResponse> searchTransaction(TransactionSearchRequest request, Pageable pageable);
}
