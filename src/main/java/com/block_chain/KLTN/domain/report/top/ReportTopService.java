package com.block_chain.KLTN.domain.report.top;

import com.block_chain.KLTN.domain.admin.report.ReportAdminTopResponse;

public interface ReportTopService {
    ReportTopResponse getReportTop();
    ReportAdminTopResponse getAdminReportTop();
}
