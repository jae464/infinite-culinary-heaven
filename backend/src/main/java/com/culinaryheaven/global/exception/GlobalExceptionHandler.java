package com.culinaryheaven.global.exception;

import com.culinaryheaven.global.exception.dto.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static java.util.stream.Collectors.toMap;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String LOG_FORMAT_ERROR = "\n[ERROR] %s %s";
    private static final String LOG_FORMAT_WARN = "\n[WARN] %s %s";

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException ex, HttpServletRequest request) {
        logger.warn(String.format(LOG_FORMAT_WARN, ex.getMessage(), request.getRequestURI()));
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(new ExceptionResponse(ex.getErrorCode().name(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex, HttpServletRequest request) {
        logger.warn(String.format(LOG_FORMAT_ERROR, ex.getMessage(), request.getRequestURI()));
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(new ExceptionResponse(ErrorCode.INTERNAL_SERVER_ERROR.name(), ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, String> result = ex.getBindingResult().getFieldErrors().stream()
                .collect(toMap(FieldError::getField, this::getFieldErrorMessage));
        return ResponseEntity.status(ErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(new ExceptionResponse(ErrorCode.INVALID_PARAMETER.name(), result.toString()));
    }

    private String getFieldErrorMessage(FieldError fieldError) {
        return fieldError.getDefaultMessage();
    }

}
