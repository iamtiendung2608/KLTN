package com.block_chain.KLTN.domain.report;

public interface ReportQueryService {

    ReportChartResponse getReportChart();

    ReportTopResponse getReportTop();

    ReportMetricResponse getReportMetric();
}
