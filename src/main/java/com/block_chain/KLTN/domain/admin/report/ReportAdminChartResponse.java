package com.block_chain.KLTN.domain.admin.report;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReportAdminChartResponse {
    private List<ReportAdminChartDetail> details;
    private Long total;
}
