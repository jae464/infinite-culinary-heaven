package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record WriterInfoResponse(

        @Schema(description = "작성자 ID")
        Long id,
        @Schema(description = "작성자 닉네임")
        String nickname,
        @Schema(description = "작성자 프로필 이미지")
        String profileImageUrl
) {

    public static WriterInfoResponse of(User user) {
        return new WriterInfoResponse(user.getId(), user.getUsername(), user.getProfileImageUrl());
    }

}
