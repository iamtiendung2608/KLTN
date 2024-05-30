package com.block_chain.KLTN.domain.admin.report;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.block_chain.KLTN.domain.report.chart.ReportChartService;
import com.block_chain.KLTN.domain.report.metric.ReportMetricService;
import com.block_chain.KLTN.domain.report.top.ReportTopService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin/report")
@RequiredArgsConstructor
public class ReportAdminController {
    private final ReportChartService reportChartService;
    private final ReportMetricService reportMetricService;
    private final ReportTopService reportTopService;

    @GetMapping("/chart")
    public ResponseEntity<?> reportChart() {
        return ResponseEntity.ok(reportChartService.getAdminReportChart());
    }

    @GetMapping("/metric")
    public ResponseEntity<?> reportMetric() {
        return ResponseEntity.ok(reportMetricService.getReportAdminMetric());
    }

    @GetMapping("/top")
    public ResponseEntity<?> reportTop() {
        return ResponseEntity.ok(reportTopService.getAdminReportTop());
    }
}
