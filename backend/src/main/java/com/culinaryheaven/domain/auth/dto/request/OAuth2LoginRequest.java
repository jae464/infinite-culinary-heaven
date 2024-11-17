package com.culinaryheaven.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record OAuth2LoginRequest(

        @NotNull
        @Schema(description = "소셜 로그인 타입")
        String oauth2Type,

        @NotNull
        @Schema(description = "OAuth2 엑세스 토큰")
        String accessToken

) {
}
