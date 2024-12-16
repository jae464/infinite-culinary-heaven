package com.culinaryheaven.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에 오류가 발생했습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터 형식이 올바르지 않습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,"접근이 제한된 리소스입니다."),
    // user
    USER_NOT_FOUNT(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),

    // contest
    TOPIC_INGREDIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 재료입니다."),
    CONTEST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 대회를 찾을수 없습니다."),

    //recipe
    RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레시피를 찾을수 없습니다."),

    // recipe like
    ALREADY_EXISTS_RECIPE_LIKE(HttpStatus.CONFLICT, "이미 좋아요 된 레시피입니다."),
    RECIPE_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 좋아요입니다."),

    // bookmark
    ALREADY_EXISTS_BOOKMARK(HttpStatus.CONFLICT, "이미 존재하는 북마크입니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 북마크입니다."),

    // security
    INVALID_OAUTH2_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth2 입니다."),
    INVALID_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 토큰 타입입니다."),
    AUTHORIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 엑세스 토큰입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 리프레쉬 토큰입니다."),

    // device
    DEVICE_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 디바이스 토큰입니다."),

    //kakao
    KAKAO_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 API 통신에 실패했습니다."),

    // image
    IMAGE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패했습니다."),
    INVALID_IMAGE_URL(HttpStatus.BAD_REQUEST, "파일 경로가 올바르지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
