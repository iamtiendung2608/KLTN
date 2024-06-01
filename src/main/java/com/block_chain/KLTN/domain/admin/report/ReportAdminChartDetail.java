package com.block_chain.KLTN.domain.admin.report;

import com.block_chain.KLTN.domain.order.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportAdminChartDetail{
    private OrderStatus status;
    private long count;
}
