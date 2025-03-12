package com.culinaryheaven.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FollowStatusResponse(
        @Schema(description = "팔로우 상태")
        FollowStatus followStatus
) {
   public static FollowStatusResponse of(FollowStatus followStatus) {
       return new FollowStatusResponse(followStatus);
   }
}
