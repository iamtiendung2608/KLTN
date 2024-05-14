package com.block_chain.KLTN.domain.transactionEvent;

import com.block_chain.KLTN.domain.transaction.TransactionEntity;

public interface TransactionEventService {
    void createTransactionEvent(TransactionEntity oldTransaction, TransactionEntity newTransaction);
}
