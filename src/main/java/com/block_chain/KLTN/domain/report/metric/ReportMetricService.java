package com.block_chain.KLTN.domain.report.metric;

import com.block_chain.KLTN.domain.report.metric.admin.MetricAdminResponse;
import com.block_chain.KLTN.domain.report.metric.user.MetricUserResponse;

public interface ReportMetricService {
    MetricAdminResponse getReportAdminMetric();
    MetricUserResponse getReportUserMetric();
}
