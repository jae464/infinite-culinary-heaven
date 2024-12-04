package com.culinaryheaven.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserInfoResponse(

        @Schema(description = "유저 ID")
        Long id,

        @Schema(description = "유저 닉네임")
        String nickname,

        @Schema(description = "유저 프로필 이미지 URL")
        String profileImageUrl

) {
        public static UserInfoResponse of(Long id, String nickname, String profileImageUrl) {
                return new UserInfoResponse(id, nickname, profileImageUrl);
        }
}
