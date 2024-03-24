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
    USER_VERIFIED("ERR_UV_0001", "%s is verified", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_VERIFICATION_CODE("ERR_UC_0001", "Invalid verification code", HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_EXISTS("ERR_UE_0001", "%s exsited", HttpStatus.CONFLICT),
    WAITING_UNDER_ALLOWED("ERR_WUA_0001", "", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_MISMATCH("", "New password and confirm password mismatch", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_PARAMETER("ERR_CM_0003", "Invalid parameter(s): %s", HttpStatus.BAD_REQUEST),
    DISABLED_USER("ERR_ACC_0006", "User is disabled.", HttpStatus.BAD_REQUEST),
    NOT_PERMISSION("ERR_ACC_0010", "No permission to access: %s", HttpStatus.NOT_ACCEPTABLE),

    OLD_PASSWORD_MISMATCH("ERR_OPM_0001", "Old password mismatch for user: %s", HttpStatus.BAD_REQUEST);
    private String code;

    @Getter
    private String message;

    private HttpStatus httpStatus;

    public static final String ERROR_SEPARATOR = ", ";

    public String getMessage(Collection<String> errorValues) {
        return String.format(message, String.join(ERROR_SEPARATOR, errorValues));
    }
}
