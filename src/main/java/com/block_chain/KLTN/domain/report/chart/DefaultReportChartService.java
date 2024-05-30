package com.block_chain.KLTN.domain.report.chart;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class DefaultReportChartService implements ReportChartService {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public ReportChartResponse getReportChart() {
        return null;
    }
}
