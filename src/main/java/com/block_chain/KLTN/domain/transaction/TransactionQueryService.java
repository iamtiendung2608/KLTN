package com.block_chain.KLTN.domain.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionQueryService {
    TransactionDetailResponse getTransactionDetail(Long id);

    Page<TransactionResponse> searchTransaction(TransactionSearchRequest request, Pageable pageable);

    List<TransactionResponse> getTransaction(Long orderId);
}
