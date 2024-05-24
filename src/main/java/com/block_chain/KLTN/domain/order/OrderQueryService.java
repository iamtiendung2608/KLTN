package com.block_chain.KLTN.domain.order;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderQueryService {
    OrderResponse getOrder(Long id);
    Page<OrderResponse> searchOrder(OrderSearchRequest request, Pageable pageable);
    Page<OrderResponse> searchOrderAssigned(OrderSearchRequest request, Pageable pageable);
    Optional<OrderDetailResponse> getOrderDetail(Long id);
    Page<OrderResponse> searchAllOrder(OrderSearchRequest request, Pageable pageable);
}
