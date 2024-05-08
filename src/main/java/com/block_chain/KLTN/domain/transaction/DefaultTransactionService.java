package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.domain.employee.EmployeeEntity;
import com.block_chain.KLTN.domain.employee.EmployeeRepository;
import com.block_chain.KLTN.domain.order.OrderEntity;
import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import com.block_chain.KLTN.domain.post_offices.PostOfficesRepository;
import com.block_chain.KLTN.domain.transactionEvent.CreateTransactionEvent;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultTransactionService implements TransactionService {
    private final PostOfficesRepository postOfficesRepository;
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        if (!orderRepository.existsById(request.orderId())){
                throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Order");
        };

        TransactionEntity transaction = TransactionEntity.builder()
                .status(request.status())
                .note(request.note())
                .orderId(request.orderId())
                .build();

        transactionRepository.save(transaction);

        applicationEventPublisher.publishEvent(new CreateTransactionEvent(null, transaction));
        return new CreateTransactionResponse(transaction.getId());
    }

    @Override
    @Transactional
    public TransactionResponse updateTransactionStatus(UpdateTransactionRequest request) {
        TransactionEntity transaction = transactionRepository.findById(request.id())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Transaction"));
        
        //To-do can not set staus deliveried if staus is sent 
        transaction.setStatus(request.status());
        transactionRepository.save(transaction);
        return transactionMapper.toResponse(transaction);
    }
}
