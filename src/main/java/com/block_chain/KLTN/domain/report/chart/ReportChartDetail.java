package com.block_chain.KLTN.domain.report.chart;

import com.block_chain.KLTN.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportChartDetail {
    OrderStatus status;
    long count;
}
