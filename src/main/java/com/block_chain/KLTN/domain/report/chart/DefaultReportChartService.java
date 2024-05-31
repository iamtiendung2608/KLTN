package com.block_chain.KLTN.domain.report.chart;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.block_chain.KLTN.domain.admin.report.ReportAdminChartDetail;
import com.block_chain.KLTN.domain.admin.report.ReportAdminChartResponse;
import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.order.OrderStatus;
import com.block_chain.KLTN.domain.order.QOrderEntity;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class DefaultReportChartService implements ReportChartService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public ReportChartResponse getReportChart() {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByEmail(userDetail.getUsername())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));
        ReportChartResponse response = new ReportChartResponse();
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        List<ReportChartDetail> details = new ArrayList<>();

        for (OrderStatus status : OrderStatus.values()) {
            JPAQuery<Integer> query = new JPAQuery<>(entityManager);
            Predicate predicate = qOrderEntity.status.eq(status)
                .and(qOrderEntity.organizationId.eq(userEntity.getOrganizationId()));

            ReportChartDetail detail = new ReportChartDetail(
                    status,
                    query.select(qOrderEntity.id)
                        .from(qOrderEntity)
                        .where(predicate)
                        .fetch().size());
            
            details.add(detail);
        }

        response.setDetails(details);
        response.setTotal(orderRepository.count());
        return response;
    }

    @Override
    public ReportAdminChartResponse getAdminReportChart() {
        ReportAdminChartResponse response = new ReportAdminChartResponse();
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        List<ReportAdminChartDetail> details = new ArrayList<>();

        for (OrderStatus status : OrderStatus.values()) {
            if (status == OrderStatus.DRAFT) continue;
            JPAQuery<Integer> query = new JPAQuery<>(entityManager);
            ReportAdminChartDetail detail = new ReportAdminChartDetail(
                    status,
                    query.select(qOrderEntity.id)
                        .from(qOrderEntity)
                        .where(qOrderEntity.status.eq(status))
                        .fetch().size());
            
            details.add(detail);
        }

        response.setDetails(details);
        response.setTotal(orderRepository.count());
        return response;
    }
}
