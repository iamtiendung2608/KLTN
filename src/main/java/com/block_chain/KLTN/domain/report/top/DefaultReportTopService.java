package com.block_chain.KLTN.domain.report.top;

import org.springframework.stereotype.Service;

import com.block_chain.KLTN.domain.admin.report.ReportAdminTopResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultReportTopService implements ReportTopService {
    @Override
    public ReportTopResponse getReportTop() {
        return null;
    }

    @Override
    public ReportAdminTopResponse getAdminReportTop() {
        return null;
    }
}
