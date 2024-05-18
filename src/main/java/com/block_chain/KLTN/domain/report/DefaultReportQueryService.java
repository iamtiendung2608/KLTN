package com.block_chain.KLTN.domain.report;

import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultReportQueryService implements ReportQueryService {

    private OrderRepository orderRepository;

    @PersistenceContext
    EntityManager entityManager;

    private final List<OrderStatus> orderStatusList = List.of(OrderStatus.CREATED, OrderStatus.SHIPPING, OrderStatus.DELIVERED, OrderStatus.RECEIVED);
    @Override
    public ReportChartResponse getReportChart() {
        return null;
    }

    @Override
    public ReportTopResponse getReportTop() {
        return null;
    }

    @Override
    public ReportMetricResponse getReportMetric() {
        return null;
    }
}
