package com.culinaryheaven.domain.auth.domain;

import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Type {
    KAKAO("kakao");
    private final String value;

    public static OAuth2Type from(String value) {
        return switch (value.toLowerCase()) {
            case "kakao" -> KAKAO;
            default -> throw new CustomException(ErrorCode.INVALID_OAUTH2_TYPE);
        };
    }
}
