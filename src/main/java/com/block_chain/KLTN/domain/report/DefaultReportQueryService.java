package com.block_chain.KLTN.domain.report;

import com.block_chain.KLTN.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class DefaultReportQueryService implements ReportQueryService {

    private OrderRepository orderRepository;

    @PersistenceContext
    EntityManager entityManager;

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
