package com.block_chain.KLTN.domain.report.top;

import java.util.Comparator;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.block_chain.KLTN.domain.admin.report.ReportAdminTopResponse;
import com.block_chain.KLTN.domain.order.OrderEntity;
import com.block_chain.KLTN.domain.order.OrderMapper;
import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.order.OrderStatus;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultReportTopService implements ReportTopService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public ReportTopResponse getReportTop() {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByEmail(userDetail.getUsername())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));
        
        return new ReportTopResponse(orderRepository.findAll()
                .stream()
                .filter(order -> order.getOrganizationId().equals(userEntity.getOrganizationId()))
                .sorted(Comparator.comparing(OrderEntity::getSubTotal, Comparator.reverseOrder()))
                .map(orderMapper::toResponse).toList()
        );
    }

    @Override
    public ReportAdminTopResponse getAdminReportTop() {
        return new ReportAdminTopResponse(orderRepository.findAll()
                .stream()
                .filter(order -> !order.getStatus().equals(OrderStatus.DRAFT))
                .sorted(Comparator.comparing(OrderEntity::getTotalPrice, Comparator.reverseOrder()))
                .map(orderMapper::toResponse).toList()
        );
    }
}