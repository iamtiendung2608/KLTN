package com.block_chain.KLTN.domain.report.top;

import com.block_chain.KLTN.domain.admin.report.ReportAdminTopResponse;
import com.block_chain.KLTN.domain.order.OrderEntity;
import com.block_chain.KLTN.domain.order.OrderMapper;
import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.order.QOrderEntity;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class DefaultReportTopService implements ReportTopService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ReportTopResponse getReportTop() {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByEmail(userDetail.getUsername())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));

        QOrderEntity qOrder = QOrderEntity.orderEntity;
        JPAQuery<OrderEntity> orderQuery = new JPAQuery<>(entityManager);

        return new ReportTopResponse(orderQuery.from(qOrder).select(qOrder)
                .where(qOrder.organizationId.eq(userEntity.getOrganizationId())).orderBy(qOrder.totalPrice.desc()).limit(5).fetch().stream().map(orderMapper::toResponse).toList());

    }

    @Override
    public ReportAdminTopResponse getAdminReportTop() {
        QOrderEntity qOrder = QOrderEntity.orderEntity;
        JPAQuery<OrderEntity> orderQuery = new JPAQuery<>(entityManager);

        return new ReportAdminTopResponse(orderQuery.from(qOrder).select(qOrder)
                .orderBy(qOrder.totalPrice.desc()).limit(5).fetch().stream().map(orderMapper::toResponse).toList());
    }
}