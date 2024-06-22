package com.block_chain.KLTN.domain.order;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderLocationResponse{
    Long id;
    String location;
}
