package com.block_chain.KLTN.domain.transferObject;

public interface TransferObjectService {
    CreateTransferObjectResponse create(TransferObjectRequest request);

    void update(Long id, TransferObjectRequest request);
}
