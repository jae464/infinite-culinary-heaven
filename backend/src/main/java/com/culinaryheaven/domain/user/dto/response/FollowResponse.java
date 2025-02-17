package com.culinaryheaven.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FollowResponse(

        @Schema(description = "팔로우한 유저 ID")
        Long userId,

        @Schema(description = "팔로우한 유저 닉네임")
        String nickname

) {
        public static FollowResponse of(Long userId, String nickname) {
                return new FollowResponse(userId, nickname);
        }
}
