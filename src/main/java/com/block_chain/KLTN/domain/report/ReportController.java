package com.block_chain.KLTN.domain.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportQueryService reportQueryService;

    @GetMapping("/chart")
    public ResponseEntity<ReportChartResponse> getChartReport() {
        return ResponseEntity.ok(reportQueryService.getReportChart());
    }

    @GetMapping("/top")
    public ResponseEntity<ReportTopResponse> getTopReport() {
        return ResponseEntity.ok(reportQueryService.getReportTop());
    }

    @GetMapping("/metric")
    public ResponseEntity<ReportMetricResponse> getMetricReport() {
        return ResponseEntity.ok(reportQueryService.getReportMetric());
    }
}
