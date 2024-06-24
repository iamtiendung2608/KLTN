package com.block_chain.KLTN.domain.order;

import com.block_chain.KLTN.domain.employee.EmployeeEntity;
import com.block_chain.KLTN.domain.employee.EmployeeRepository;
import com.block_chain.KLTN.domain.location_tag.LocationTagEV;
import com.block_chain.KLTN.domain.location_tag.LocationTagEntity;
import com.block_chain.KLTN.domain.location_tag.LocationTagRepository;
import com.block_chain.KLTN.domain.transaction.TransactionEntity;
import com.block_chain.KLTN.domain.transaction.TransactionRepository;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.domain.user.UserService;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultOrderQueryService implements OrderQueryService{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final LocationTagRepository locationTagRepository;
    private final EmployeeRepository employeeRepository;

    private final UserService userService;

    @Override
    public OrderResponse getOrder(Long id) {
        return orderRepository.findById(id).map(orderMapper::toResponse)
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Order"));
    }

    @Override
    public Page<OrderResponse> searchOrder(OrderSearchRequest request, Pageable pageable) {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByEmail(userDetail.getUsername())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));

        if (userService.getUserRole(userDetail.getUsername()).equals("employee")) {
            EmployeeEntity employee = employeeRepository.findByEmail(userDetail.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Employee email not found"));
            return orderRepository.findAll(request.employeeToPredicte(employee.getId()), pageable).map(orderMapper::toResponse);
        }
        return orderRepository.findAll(request.toPredicate(userEntity.getOrganizationId()), pageable).map(orderMapper::toResponse);
    }

    @Override
    public Page<OrderResponse> searchOrderAssigned(OrderSearchRequest request, Pageable pageable) {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeEntity employee = employeeRepository.findByEmail(userDetail.getUsername())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Employee email not found"));
        
        return orderRepository.findAll(request.orderAssigned(employee.getId()), pageable).map(orderMapper::toResponse);
    }

    @Override
    public Optional<OrderDetailResponse> getOrderDetail(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDetailResponse);
    }

    @Override
    public Page<OrderResponse> searchAllOrder(OrderSearchRequest request, Pageable pageable) {
        return orderRepository.findAll(request.allOrder(), pageable).map(orderMapper::toResponse);
    }

    @Override
    public OrderLocationResponse getOrderLocation(Long id) {
        OrderEntity order = orderRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Order"));
        String location, currentOwner;
        switch (order.getStatus()) {
            case DRAFT:
            case CREATED:
                location = getLocation(order.getSenderObject().getCustomer().getLocationTag(), 
                                            order.getSenderObject().getCustomer().getAddress());
                currentOwner = order.getSenderObject().getCustomer().getFullName();
                break; 
            case DELIVERED:
                location = getLocation(order.getReceiverObject().getCustomer().getLocationTag(), 
                                            order.getReceiverObject().getCustomer().getAddress());
                currentOwner = order.getReceiverObject().getCustomer().getFullName();
                break;
            default:
                TransactionEntity transaction = transactionRepository.findLastTransaction(order.getId());
                location = getLocation(transaction.getPostOffice().getLocationTag(), 
                                            transaction.getPostOffice().getAddress());
                
                currentOwner = transaction.getPostOffice().getName();
                break;
        }

        return new OrderLocationResponse(order.getId(), location, currentOwner, order.getStatus());
    }

    private String getLocation(LocationTagEntity locationTag, String address){
        return String.format("%s, %s", address, locationTag.toString());
    }
}
