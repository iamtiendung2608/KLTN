package com.block_chain.KLTN.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusinessException extends RuntimeException {
    private ErrorMessage errorMessage;
    private Errors errors;

    public BusinessException(ErrorMessage errorMessage) {
        this(errorMessage, (Exception) null);
    }

    public BusinessException(ErrorMessage errorMessage, String... args) {
        super(String.format(errorMessage.getMessage(), (Object[]) args));
        this.errorMessage = errorMessage;
    }

    public BusinessException(ErrorMessage errorMessage, List<String> args) {
        super(errorMessage.getMessage(args));
        this.errorMessage = errorMessage;
    }

    public BusinessException(ErrorMessage errorMessage, Exception enclosedException, String... args) {
        super(String.format(errorMessage.getMessage(), args, enclosedException));
        this.errorMessage = errorMessage;
    }

    public BusinessException(ErrorMessage errorMessage, Exception enclosedException) {
        this(errorMessage.getMessage(), enclosedException);
        this.errorMessage = errorMessage;
    }

    public BusinessException(String message, Exception enclosedException) {
        super(message, enclosedException);
        this.errorMessage = ErrorMessage.UNKNOWN_ERROR;
    }

    public BusinessException(Errors errors) {
        this(errors, null);
    }

    /**
     * Business exception constructor.
     * @param errors errors.
     * @param enclosedException enclosed exception.
     */
    public BusinessException(Errors errors, Exception enclosedException) {
        super(String.format("Provider Error, Context : %s Code : %s Message : %s",
                errors.getContext(),
                errors.getCode(),
                errors.getMessage()), enclosedException);
        this.errors = errors;
    }
}
