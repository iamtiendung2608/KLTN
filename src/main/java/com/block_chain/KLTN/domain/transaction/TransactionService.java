package com.block_chain.KLTN.domain.transaction;

public interface TransactionService {
    CreateTransactionResponse createTransaction(CreateTransactionRequest request);
}
