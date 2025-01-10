package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.recipe.domain.RecipeLike;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

public record RecipeLikesResponse(
        @Schema(description = "좋아요 한 레시피들")
        List<RecipeLikeResponse> recipeLikes
) {
    public static RecipeLikesResponse of(Page<RecipeLike> recipeLikes) {
        List<RecipeLikeResponse> recipeLikesResponse = recipeLikes.getContent()
                .stream()
                .map(RecipeLikeResponse::of)
                .toList();
        return new RecipeLikesResponse(recipeLikesResponse);
    }
}
