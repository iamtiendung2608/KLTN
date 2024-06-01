package com.block_chain.KLTN.domain.report.top;

import com.block_chain.KLTN.domain.order.OrderResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReportTopResponse {
    List<OrderResponse> response;
}
