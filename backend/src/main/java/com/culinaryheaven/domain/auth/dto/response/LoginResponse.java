package com.culinaryheaven.domain.auth.dto.response;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
