package com.culinaryheaven.domain.user.dto.response;

import com.culinaryheaven.domain.user.domain.Follow;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record FollowsResponse(
        @Schema(description = "팔로우한 유저들 정보")
        List<FollowResponse> follows
) {
    public static FollowsResponse of(List<Follow> follows) {
        return new FollowsResponse(
                follows.stream().map(FollowResponse::of).toList()
        );
    }
}
