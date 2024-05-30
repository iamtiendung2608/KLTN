package com.block_chain.KLTN.domain.report.chart;

import com.block_chain.KLTN.domain.order.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportChartDetail {
    OrderStatus status;
    long count;
}
