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
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransactionEntity oldTransaction = transactionRepository.findLastTransaction(request.orderId());
        OrderEntity order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Order"));
        
        EmployeeEntity employee = null;
        if (request.status() != TransactionStatus.CREATED){
            employee = employeeRepository.findByEmail(userDetail.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Employee"));
        }
        
        TransactionEntity transaction = TransactionEntity.builder()
            .status(request.status())
            .note(request.note())
            .orderId(request.orderId())
            .order(order)
            .build();
        PostOfficesEntity postOffice = null;

        if (Objects.nonNull(order.getEmployeeId()) && 
            (!order.getEmployeeId().equals(employee.getId()))) {
            throw new BusinessException(ErrorMessage.INVALID_REQUEST_PARAMETER, "You dont have permission to update this order");
        }

        if (Objects.nonNull(oldTransaction)) {
            if (oldTransaction.getStatus().equals(TransactionStatus.DELIVERED) || 
                oldTransaction.getStatus().getStep() > request.status().getStep() ||
                oldTransaction.getStatus() == request.status()) {
                throw new BusinessException(ErrorMessage.INVALID_REQUEST_PARAMETER, "Fail to update transaction status");
            }
        }

        if (request.postOfficeId() != null){
            postOffice = postOfficesRepository.findById(request.postOfficeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "PostOffice"));
        }

        switch (transaction.getStatus()) {
            case RECEIVED:{
                transaction.setPostOffice(postOffice);
                transaction.setPostOfficeId(postOffice.getId());
                transaction.setEmployee(null);
                transaction.setEmployeeId(null);

                order.getSenderObject().setPostOffices(postOffice);
                order.getSenderObject().setPostOfficeId(postOffice.getId());
                order.setEmployee(null);
                order.setEmployeeId(null);
                break;
            }
            case DELIVERED:{
                order.getReceiverObject().setPostOffices(postOffice);
                order.getReceiverObject().setPostOfficeId(postOffice.getId());
            }
            case TRANSPORTED:{
                if (Objects.nonNull(request.postOfficeId())){
                    if (!oldTransaction.getPostOfficeId().equals(request.postOfficeId())) {
                        throw new BusinessException(ErrorMessage.INVALID_REQUEST_PARAMETER, "The postoffice is not the same when transporting");
                    }
                }else{
                    postOffice = oldTransaction.getPostOffice();
                }
                
                transaction.setPostOffice(postOffice);
                transaction.setPostOfficeId(postOffice.getId());
                transaction.setEmployee(null);
                transaction.setEmployeeId(null);

                order.setEmployee(null);
                order.setEmployeeId(null);
                
                break;
            }
            case TRANSPORTING:{
                if (oldTransaction.getPostOfficeId().equals(request.postOfficeId())) {
                    throw new BusinessException(ErrorMessage.INVALID_REQUEST_PARAMETER, "Cannot transport to the same postoffice");
                } 

                transaction.setPostOffice(postOffice);
                transaction.setPostOfficeId(postOffice.getId());
                transaction.setEmployee(employee);
                transaction.setEmployeeId(employee.getId());

                order.setEmployee(employee);
                order.setEmployeeId(employee.getId());
                break;
            }
            case DELIVERING:{
                if (Objects.nonNull(request.postOfficeId())){
                    if (!oldTransaction.getPostOfficeId().equals(request.postOfficeId())) {
                        throw new BusinessException(ErrorMessage.INVALID_REQUEST_PARAMETER, "This postoffice is not contain this order");
                    }
                }else{
                    postOffice = oldTransaction.getPostOffice();
                }

                transaction.setPostOffice(postOffice);
                transaction.setPostOfficeId(postOffice.getId());
                transaction.setEmployee(employee);
                transaction.setEmployeeId(employee.getId());

                order.setEmployee(employee);
                order.setEmployeeId(employee.getId());
                break;
            }
            case CREATED:{
                if (Objects.nonNull(oldTransaction)) {
                    throw new BusinessException(ErrorMessage.INVALID_REQUEST_PARAMETER, "Đơn hàng đã được gửi đến kho hàng");
                }
                break;
            }
            default:
                throw new BusinessException(ErrorMessage.INVALID_REQUEST_PARAMETER, "Transaction Status");
        }
        
        orderRepository.save(order);
        transaction = transactionRepository.save(transaction);
        applicationEventPublisher.publishEvent(new CreateTransactionEvent(oldTransaction, transaction));
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
