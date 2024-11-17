package com.culinaryheaven.domain.auth.dto.response;

import com.culinaryheaven.domain.auth.infrastructure.dto.response.UserInfoResponse;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
