package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.domain.employee.EmployeeEntity;
import com.block_chain.KLTN.domain.employee.EmployeeRepository;
import com.block_chain.KLTN.domain.order.OrderEntity;
import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.postOffices.PostOfficesEntity;
import com.block_chain.KLTN.domain.postOffices.PostOfficesRepository;
import com.block_chain.KLTN.domain.transactionEvent.CreateTransactionEvent;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        TransactionEntity oldTransaction = transactionRepository.findLastTransaction(request.orderId());
        
        OrderEntity order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Order"));

        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeEntity employee = employeeRepository.findByEmail(userDetail.getUsername())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Employee"));

        TransactionEntity transaction = TransactionEntity.builder()
                    .status(request.status())
                    .note(request.note())
                    .orderId(request.orderId())
                    .order(order)
                    .employeeId(employee.getId())
                    .postOfficeId(request.postOfficeId())
                    .build();

        switch (transaction.getStatus()) {
            case RECEIVED:
            case TRANSPORTED:
            case DELIVERING:
            case DELIVERIED:
            case TRANSPORTING:{
                PostOfficesEntity postOffice = postOfficesRepository.findById(request.postOfficeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "PostOffice"));
                EmployeeEntity employeeEntity = employeeRepository.findById(employee.getId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Employee"));

                transaction.setPostOffice(postOffice);
                transaction.setEmployee(employeeEntity);
                applicationEventPublisher.publishEvent(new CreateTransactionEvent(oldTransaction, transaction));
                break;
            }
                
            case CREATED:{
                if (Objects.nonNull(oldTransaction)) {
                    throw new BusinessException(ErrorMessage.INVALID_REQUEST_PARAMETER, "Đơn hàng đã được gửi đến kho hàng");
                }
                applicationEventPublisher.publishEvent(new CreateTransactionEvent(null, transaction));
                break;
            }
            default:
                throw new BusinessException(ErrorMessage.INVALID_REQUEST_PARAMETER, "Transaction Status");
        }
        
        transaction = transactionRepository.save(transaction);
        return new CreateTransactionResponse(transaction.getId());
        
    }


    /*
    * We dont need this
    */
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
