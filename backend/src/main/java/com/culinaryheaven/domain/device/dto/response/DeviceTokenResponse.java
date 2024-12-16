package com.culinaryheaven.domain.device.dto.response;

import com.culinaryheaven.domain.device.domain.DeviceToken;
import io.swagger.v3.oas.annotations.media.Schema;

public record DeviceTokenResponse(

        @Schema(description = "디바이스 토큰")
        String token

) {

    public static DeviceTokenResponse of(DeviceToken deviceToken) {
        return new DeviceTokenResponse(deviceToken.getToken());
    }
}
