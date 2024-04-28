package com.block_chain.KLTN.domain.order;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.block_chain.KLTN.domain.item.ItemEntity;
import com.block_chain.KLTN.domain.item.ItemMapper;
import com.block_chain.KLTN.domain.item.ItemRepository;
import com.block_chain.KLTN.domain.order.order_item.OrderItemEntity;
import com.block_chain.KLTN.domain.order.order_item.OrderItemKey;
import com.block_chain.KLTN.domain.order.order_item.OrderItemMapper;
import com.block_chain.KLTN.domain.order.order_item.OrderItemRepository;
import com.block_chain.KLTN.domain.order.order_item.OrderItemRequest;
import com.block_chain.KLTN.domain.order.order_item.OrderItemResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultOrderService implements OrderService{
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest orderReq) {
        // Handling Order Entity
        int totalPrice = orderReq.items()
            .stream().reduce(0, (subTotal, item) -> subTotal + item.price() * item.quantity(), Integer::sum); 
        float totalWeight = orderReq.items()
            .stream().reduce(0f, (subTotal, item) -> subTotal + item.weight() * item.quantity(), Float::sum);
        OffsetDateTime estimatedDeliveryAt = OffsetDateTime.now().plusDays(7L);
        
        OrderEntity orderEntity = OrderEntity.builder()
            .status(orderReq.status())
            .totalPrice(totalPrice)
            .totalWeight(totalWeight)
            .estimatedDeliveryAt(estimatedDeliveryAt)
            .deliveryType(orderReq.deliveryType())
            .note(orderReq.note())
            .paidType(orderReq.paidType())
            .build();   

        orderEntity = orderRepository.saveAndFlush(orderEntity);
        
        // Handling OrderItem Entity
        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (OrderItemRequest item : orderReq.items()) {
            ItemEntity itemEntity = itemRepository.saveAndFlush(itemMapper.toEntity(item));

            OrderItemEntity orderItem = OrderItemEntity.builder()
                .id(new OrderItemKey(orderEntity.getId(), itemEntity.getId()))
                .order(orderEntity)
                .item(itemEntity)
                .quantity(item.quantity())
                .build();

            orderItems.add(orderItem);
        }
        orderEntity.setOrderItems(orderItems);
        orderItemRepository.saveAllAndFlush(orderItems);

        return orderMapper.toResponse(orderEntity);
    }

    @Override
    public OrderResponse updateOrderStatus(OrderUpdateStatusRequest orderReq) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus'");
    }
}
