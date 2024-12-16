package com.culinaryheaven.domain.device.dto.request;

import com.culinaryheaven.domain.device.domain.DeviceToken;
import io.swagger.v3.oas.annotations.media.Schema;

public record DeviceTokenPersistRequest(
        @Schema(description = "디바이스 토큰")
        String token
) {
    public DeviceToken toEntity() {
        return DeviceToken.builder()
                .token(token)
                .build();
    }
}
