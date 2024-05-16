package com.block_chain.KLTN.domain.transferObject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransferObjectQueryService {
    TransferObjectResponse getTransferObject(Long id);

    Page<TransferObjectResponse> searchTransferObject(TransferObjectSearchRequest request, Pageable pageable);
}
