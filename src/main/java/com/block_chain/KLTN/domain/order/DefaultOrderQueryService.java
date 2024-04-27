package com.block_chain.KLTN.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultOrderQueryService implements OrderQueryService{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse getOrder(Long id) {
        OrderEntity entity = orderRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Order"));
        return orderMapper.toResponse(entity);
    }

    @Override
    public Page<OrderResponse> searchOrder(OrderSearchRequest request, Pageable pageable) {
        return orderRepository.findAll(request.toPredicate(), pageable).map(orderMapper::toResponse);
    }
}
