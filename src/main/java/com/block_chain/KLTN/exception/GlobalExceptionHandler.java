package com.block_chain.KLTN.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Errors globalExceptionHandler(Exception e, WebRequest request) {
        return Errors.builder()
                .code(ErrorMessage.UNKNOWN_ERROR.getCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public Errors globalExceptionHandler(AccessDeniedException e, WebRequest request) {
        logger.error("Caught exception", e);
        return Errors.builder()
                .code(ErrorMessage.NOT_PERMISSION.getCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Errors globalExceptionHandler(DisabledException e, WebRequest request) {
        logger.error("Caught exception", e);
        return Errors.builder()
                .code(ErrorMessage.DISABLED_USER.getCode())
                .message(e.getMessage())
                .build();
    }

    /**
     * Handle business exception.
     * @param e exception need to be handle.
     * @param response Servlet Response.
     * @return Error response.
     */
    @ExceptionHandler(value = {BusinessException.class})
    @ResponseBody
    public Errors handleBusinessException(BusinessException e, HttpServletResponse response) {
        ErrorMessage error = e.getErrorMessage();
        response.setStatus(error.getHttpStatus().value());
        return Errors.builder()
                .code(error.getCode())
                .message(e.getMessage())
                .build();
    }

    /**
     * Handle ConstraintViolationException exception.
     * @param ex exception.
     * @return Error response.
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Errors handleConstraintViolation(ConstraintViolationException ex) {
        Map<Path, String> messages = ex.getConstraintViolations()
                .stream()
                .collect(Collectors
                        .toMap(
                                ConstraintViolation::getPropertyPath,
                                ConstraintViolation::getMessage));
        return Errors.builder()
                .code(ErrorMessage.INVALID_REQUEST_PARAMETER.getCode())
                .message(ErrorMessage.INVALID_REQUEST_PARAMETER
                        .getMessage(new ArrayList<>(messages.values())))
                .errors(messages.entrySet().stream().map(pathStringEntry -> Errors.ErrorDetail
                                .builder()
                                .message(pathStringEntry.getValue())
                                .name(pathStringEntry.getKey().toString())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        Set<String> fieldNames = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.toCollection(TreeSet::new));

        List<Errors.ErrorDetail> errorDetails = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> Errors.ErrorDetail.builder()
                        .name(e.getObjectName())
                        .message(e.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(Errors.builder()
                .code(ErrorMessage.INVALID_REQUEST_PARAMETER.getCode())
                .message(ErrorMessage.INVALID_REQUEST_PARAMETER.getMessage(fieldNames))
                .errors(errorDetails)
                .build());
    }
}
