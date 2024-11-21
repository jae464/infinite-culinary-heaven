package com.culinaryheaven.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ReissueRequest(

        @NotNull
        @Schema(description = "리프레쉬 토큰")
        String refreshToken

) {
}
