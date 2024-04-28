package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.domain.employee.EmployeeEntity;
import com.block_chain.KLTN.domain.employee.EmployeeRepository;
import com.block_chain.KLTN.domain.order.OrderEntity;
import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import com.block_chain.KLTN.domain.post_offices.PostOfficesRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        PostOfficesEntity postOffice = postOfficesRepository.findById(request.postOfficeId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Post Office"));
        OrderEntity order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Order"));

        TransactionEntity transaction = TransactionEntity.builder()
                .status(request.status())
                .note(request.note())
                .postOfficeId(postOffice.getId())
                .orderId(order.getId())
                .build();

        if (!Objects.isNull(request.employeeId())) {
            EmployeeEntity employee = employeeRepository.findById(request.employeeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Employee"));
            transaction.setEmployeeId(employee.getId());
        }

        transactionRepository.save(transaction);
        return new CreateTransactionResponse(transaction.getId());

    }
}
