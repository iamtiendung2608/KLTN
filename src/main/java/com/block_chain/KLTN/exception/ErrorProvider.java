package com.block_chain.KLTN.exception;

public interface ErrorProvider {
    String getCode();

    String getMessage();

    default String getContext() {
        return getClass().getName();
    }
}
