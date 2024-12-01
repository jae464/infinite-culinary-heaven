package com.culinaryheaven.global.exception.dto;

public record ExceptionResponse(
        String errorCode,
        String message
) {

}
