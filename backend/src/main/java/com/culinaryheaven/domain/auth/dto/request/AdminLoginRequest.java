package com.culinaryheaven.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AdminLoginRequest(

        @NotNull
        @Schema(description = "관리자 ID")
        String id,

        @NotNull
        @Schema(description = "관리자 패스워드")
        String password

) {

}
