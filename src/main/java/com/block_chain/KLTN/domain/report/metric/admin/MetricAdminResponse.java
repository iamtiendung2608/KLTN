package com.block_chain.KLTN.domain.report.metric.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricAdminResponse {
    private long currentOrder;
    private long totalOrder;
    private long currentUser;
    private long totalUser;
    private Float currentShip;
    private Float totalShip;
}
