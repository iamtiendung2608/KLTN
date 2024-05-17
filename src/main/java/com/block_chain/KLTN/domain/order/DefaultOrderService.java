package com.block_chain.KLTN.domain.order;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.block_chain.KLTN.domain.item.ItemEntity;
import com.block_chain.KLTN.domain.item.ItemMapper;
import com.block_chain.KLTN.domain.item.ItemRepository;
import com.block_chain.KLTN.domain.order.order_item.OrderItemEntity;
import com.block_chain.KLTN.domain.order.order_item.OrderItemKey;
import com.block_chain.KLTN.domain.order.order_item.OrderItemRepository;
import com.block_chain.KLTN.domain.order.order_item.OrderItemRequest;
import com.block_chain.KLTN.domain.organization.OrganizationRepository;
import com.block_chain.KLTN.domain.transaction.CreateTransactionRequest;
import com.block_chain.KLTN.domain.transaction.TransactionService;
import com.block_chain.KLTN.domain.transaction.TransactionStatus;
import com.block_chain.KLTN.domain.transfer_object.TransferObjectEntity;
import com.block_chain.KLTN.domain.transfer_object.TransferObjectRepository;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultOrderService implements OrderService{
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final TransferObjectRepository transferObjectRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    private final TransactionService transactionService;

    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;
    
    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest orderReq) {
        TransferObjectEntity sender = transferObjectRepository.findById(orderReq.senderObjectId())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Sender transfer object "));
        TransferObjectEntity receiver = transferObjectRepository.findById(orderReq.receiverObjectId())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Receiver transfer object "));
        
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByEmail(userDetail.getUsername())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));

        if (!organizationRepository.existsById(user.getOrganizationId())){
            throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Organization");
        }

        // Handling Order Entity
        int totalPrice = orderReq.items()
            .stream().reduce(0, (subTotal, item) -> subTotal + item.price() * item.quantity(), Integer::sum); 
        Float totalWeight = orderReq.items()
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
            .senderObjectId(orderReq.senderObjectId())
            .receiverObjectId(orderReq.receiverObjectId())
            .organizationId(user.getOrganizationId())
            .senderObject(sender)
            .receiverObject(receiver)
            .build();   
        
        orderEntity = orderRepository.save(orderEntity);
        
        // Handling OrderItem Entity
        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (OrderItemRequest item : orderReq.items()) {
            ItemEntity itemEntity = itemRepository.save(itemMapper.toEntity(item));

            OrderItemEntity orderItem = OrderItemEntity.builder()
                .id(new OrderItemKey(orderEntity.getId(), itemEntity.getId()))
                .order(orderEntity)
                .item(itemEntity)
                .quantity(item.quantity())
                .build();

            orderItems.add(orderItem);
        }
        orderEntity.setOrderItems(orderItems);
        orderItemRepository.saveAll(orderItems);

        if (orderReq.status() == OrderStatus.CREATED) {
            createTransaction(orderEntity.getNote(), orderEntity.getId());
        }

        return orderMapper.toResponse(orderEntity);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long id, OrderUpdateStatusRequest orderReq) {
        OrderEntity order = orderRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Order"));
        
        if (orderReq.status() == OrderStatus.CREATED) {
            order.setStatus(orderReq.status());
            orderRepository.save(order);
            createTransaction(order.getNote(), order.getId());
        }
        
        return orderMapper.toResponse(order);
    }

    private void createTransaction(String note, Long id){
        transactionService.createTransaction(
                new CreateTransactionRequest(TransactionStatus.CREATED, 
                    note, 
                    id, 
                    null));
    }
}
