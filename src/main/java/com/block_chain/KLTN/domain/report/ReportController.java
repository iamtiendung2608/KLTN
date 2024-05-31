package com.block_chain.KLTN.domain.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.block_chain.KLTN.domain.report.chart.ReportChartResponse;
import com.block_chain.KLTN.domain.report.chart.ReportChartService;
import com.block_chain.KLTN.domain.report.metric.ReportMetricService;
import com.block_chain.KLTN.domain.report.metric.user.MetricUserResponse;
import com.block_chain.KLTN.domain.report.top.ReportTopResponse;
import com.block_chain.KLTN.domain.report.top.ReportTopService;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportTopService reportTopService;
    private final ReportMetricService reportMetricService;
    private final ReportChartService reportChartService;

    @GetMapping("/chart")
    public ResponseEntity<ReportChartResponse> getChartReport() {
        return ResponseEntity.ok(reportChartService.getReportChart());
    }

    @GetMapping("/top")
    public ResponseEntity<ReportTopResponse> getTopReport() {
        return ResponseEntity.ok(reportTopService.getReportTop());
    }

    @GetMapping("/metric-user")
    public ResponseEntity<MetricUserResponse> getUserMetric() {
        return ResponseEntity.ok(reportMetricService.getReportUserMetric());
    }
}
