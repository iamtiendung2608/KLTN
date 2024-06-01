package com.block_chain.KLTN.domain.admin.report;

import java.util.List;

import com.block_chain.KLTN.domain.order.OrderResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ReportAdminTopResponse{
    List<OrderResponse> response;
}
