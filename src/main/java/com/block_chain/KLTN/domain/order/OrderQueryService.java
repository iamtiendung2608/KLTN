package com.block_chain.KLTN.domain.order;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderQueryService {
    OrderResponse getOrder(Long id);
    Page<OrderResponse> searchOrder(OrderSearchRequest request, Pageable pageable);
}
