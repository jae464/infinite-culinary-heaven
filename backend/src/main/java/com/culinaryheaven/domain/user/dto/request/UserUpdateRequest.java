package com.culinaryheaven.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(

    @NotBlank(message = "유저 닉네임은 비어있으면 안됩니다.")
    @Schema(description = "유저 닉네임")
    String userNickname

) {
}
