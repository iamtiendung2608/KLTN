package com.block_chain.KLTN.domain.transfer_object;

public interface TransferObjectService {
    CreateTransferObjectResponse create(TransferObjectRequest request);

    void update(Long id, TransferObjectRequest request);
}
