package com.block_chain.KLTN.domain.report.chart;

import com.block_chain.KLTN.domain.admin.report.ReportAdminChartDetail;
import com.block_chain.KLTN.domain.admin.report.ReportAdminChartResponse;
import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.order.OrderStatus;
import com.block_chain.KLTN.domain.order.QOrderEntity;
import com.block_chain.KLTN.security.UserPrincipal;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultReportChartService implements ReportChartService {
    private final OrderRepository orderRepository;
    private final List<OrderStatus> statuses = List.of(OrderStatus.CREATED, OrderStatus.DELIVERED, OrderStatus.TRANSPORTED);

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public ReportChartResponse getReportChart() {
        UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ReportChartResponse response = new ReportChartResponse();
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;

        JPAQuery<Tuple> query = new JPAQuery<>(entityManager)
            .select(qOrderEntity.status, qOrderEntity.count())
            .from(qOrderEntity)
            .where(qOrderEntity.status.in(statuses).and(qOrderEntity.organizationId.eq(userDetails.getOrganizationId())))
            .groupBy(qOrderEntity.status);

        List<Tuple> results = query.fetch();

        Map<OrderStatus, Long> statusCounts = results.stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(qOrderEntity.status),
                tuple -> tuple.get(qOrderEntity.count())
            ));

        List<ReportChartDetail> details = statusCounts.entrySet().stream()
            .map(entry -> {
                ReportChartDetail detail = new ReportChartDetail();
                detail.setStatus(entry.getKey());
                detail.setCount(entry.getValue());
                return detail;
            })
            .collect(Collectors.toList());

        response.setDetails(details);
        response.setTotal(orderRepository.count());

        return response;
    }

    @Override
    public ReportAdminChartResponse getAdminReportChart() {
        ReportAdminChartResponse response = new ReportAdminChartResponse();
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;

        JPAQuery<Tuple> query = new JPAQuery<>(entityManager)
            .select(qOrderEntity.status, qOrderEntity.count())
            .from(qOrderEntity)
            .where(qOrderEntity.status.in(statuses))
            .groupBy(qOrderEntity.status);

        List<Tuple> results = query.fetch();

        Map<OrderStatus, Long> statusCounts = results.stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(qOrderEntity.status),
                tuple -> tuple.get(qOrderEntity.count())
            ));

        List<ReportAdminChartDetail> details = statusCounts.entrySet().stream()
            .map(entry -> {
                ReportAdminChartDetail detail = new ReportAdminChartDetail();
                detail.setStatus(entry.getKey());
                detail.setCount(entry.getValue());
                return detail;
            })
            .collect(Collectors.toList());

        response.setDetails(details);
        response.setTotal(orderRepository.count());
        return response;
    }
}
