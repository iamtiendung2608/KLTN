package com.block_chain.KLTN.domain.report.chart;

import com.block_chain.KLTN.domain.admin.report.ReportAdminChartResponse;

public interface ReportChartService {
    ReportChartResponse getReportChart();
    ReportAdminChartResponse getAdminReportChart();
}
