package com.block_chain.KLTN.domain.report.chart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportChartResponse {
    private List<ReportChartDetail> details;
    private Long total;
}
