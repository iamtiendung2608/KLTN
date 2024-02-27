package com.block_chain.KLTN.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Collection;

@Getter
@ToString
@AllArgsConstructor
public enum ErrorMessage {
    UNKNOWN_ERROR("ERR_CM_0001", "Internal server error. Please try again", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR("Internal Server Error", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_NOT_FOUND("ERR_NF_0001", "%s not found", HttpStatus.NOT_FOUND),
    RESOURCE_EXISTS("ERR_UE_0001", "%s exsited", HttpStatus.CONFLICT);
    private String code;

    @Getter
    private String message;

    private HttpStatus httpStatus;

    public static final String ERROR_SEPARATOR = ", ";

    public String getMessage(Collection<String> errorValues) {
        return String.format(message, String.join(ERROR_SEPARATOR, errorValues));
    }
}
