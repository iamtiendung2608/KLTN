package com.block_chain.KLTN.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Errors implements ErrorProvider {
    private String code;
    private String message;

    @Builder.Default
    List<ErrorDetail> errors = Collections.emptyList();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorDetail {
        String name;
        String message;
    }
}
