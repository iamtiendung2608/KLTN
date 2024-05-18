package com.block_chain.KLTN.domain.report;

import com.block_chain.KLTN.domain.order.OrderStatus;

import java.util.List;

public record ReportChartResponse(
        List<ReportChartDetail> details,
        Long total
) {
    public record ReportChartDetail(
        OrderStatus status,
        Long count
    ){}
}
