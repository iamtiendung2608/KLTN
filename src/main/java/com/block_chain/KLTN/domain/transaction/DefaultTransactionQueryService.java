package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTransactionQueryService implements TransactionQueryService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionDetailResponse getTransactionDetail(Long id) {
        TransactionEntity transaction = transactionRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Transaction"));
        return transactionMapper.toDetailResponse(transaction);
    }

    @Override
    public Page<TransactionResponse> searchTransaction(TransactionSearchRequest request, Pageable pageable) {
        return transactionRepository.findAll(request.toPredicate(), pageable).map(transactionMapper::toResponse);
    }

    @Override
    public List<TransactionResponse> getTransaction(Long orderId) {
        return transactionRepository.findAllByOrderId(orderId)
                .stream().map(transactionMapper::toResponse).toList();
    }
}
