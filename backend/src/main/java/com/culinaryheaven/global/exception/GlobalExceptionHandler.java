package com.culinaryheaven.global.exception;

import com.culinaryheaven.domain.contest.exception.TopicIngredientNotFoundException;
import com.culinaryheaven.global.exception.dto.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String LOG_FORMAT_ERROR = "\n[ERROR] %s %s";
    private static final String LOG_FORMAT_WARN = "\n[WARN] %s %s";

    @ExceptionHandler(TopicIngredientNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTopicIngredientNotFoundException(TopicIngredientNotFoundException ex, HttpServletRequest request) {
        logger.warn(String.format(LOG_FORMAT_WARN, ex.getMessage(), request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex, HttpServletRequest request) {
        logger.warn(String.format(LOG_FORMAT_ERROR, ex.getMessage(), request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage()));
    }

}
