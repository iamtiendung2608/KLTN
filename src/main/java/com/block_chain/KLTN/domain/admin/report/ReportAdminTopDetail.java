package com.block_chain.KLTN.domain.admin.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ReportAdminTopDetail {
    private Long postOfficesId;
    private Integer totalOrders;
    private Integer numOfCompleteOrders; // Number of order that have been deliveried
}
