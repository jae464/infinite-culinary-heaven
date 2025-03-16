package com.culinaryheaven.domain.user.dto.response;

import com.culinaryheaven.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserInfoResponse(

        @Schema(description = "유저 ID")
        Long id,

        @Schema(description = "유저 닉네임")
        String nickname,

        @Schema(description = "유저 프로필 이미지 URL")
        String profileImageUrl,

        @Schema(description = "팔로워 수")
        Integer followerCount,

        @Schema(description = "팔로잉 수")
        Integer followingCount

) {
    public static UserInfoResponse of(User user) {
        return new UserInfoResponse(user.getId(), user.getUsername(), user.getProfileImageUrl(), null, null);
    }

    public static UserInfoResponse of(User user, int followerCount, int followingCount) {
        return new UserInfoResponse(user.getId(), user.getUsername(), user.getProfileImageUrl(),followerCount, followingCount);
    }
}
