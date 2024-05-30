package com.block_chain.KLTN.domain.report.metric.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MetricUserResponse {
    private long currentOrder;
    private long totalOrder;
    private long currentCustomer;
    private long totalCustomer;
    private Float currentSubtotal;
    private Float totalSubtotal;
}
