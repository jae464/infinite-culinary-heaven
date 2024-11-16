package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.contest.dto.response.ContestResponse;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;

public record RecipeResponse(

        @Schema(description = "레시피 제목")
        String title,

        @Schema(description = "레시피 대표 이미지 URL")
        String thumbnailImage,

        @Schema(description = "레시피가 등록된 대회 정보")
        ContestResponse contest
) {
    public static RecipeResponse of(Recipe recipe) {
        return new RecipeResponse(recipe.getTitle(), recipe.getThumbnailImage(), ContestResponse.of(recipe.getContest()));
    }
}
