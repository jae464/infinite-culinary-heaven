package com.culinaryheaven.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // contest
    TOPIC_INGREDIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 재료입니다."),
    CONTEST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 대회를 찾을수 없습니다."),

    //recipe
    RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레시피를 찾을수 없습니다."),

    // security
    INVALID_OAUTH2_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth2 입니다."),
    INVALID_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 토큰 타입입니다."),
    AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 엑세스 토큰입니다."),

    //kakao
    KAKAO_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 API 통신에 실패했습니다."),

    // image
    IMAGE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패했습니다."),
    INVALID_IMAGE_URL(HttpStatus.BAD_REQUEST, "파일 경로가 올바르지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
