package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.contest.dto.response.ContestResponse;
import com.culinaryheaven.domain.recipe.domain.Ingredient;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RecipeResponse(

        @Schema(description = "레시피 ID")
        Long id,

        @Schema(description = "레시피 제목")
        String title,

        @Schema(description = "레시피 설명")
        String description,

        @Schema(description = "레시피 대표 이미지 URL")
        String thumbnailImage,

        @Schema(description = "레시피 스텝")
        List<StepResponse> steps,

        @Schema(description = "사용된 재료")
        List<IngredientResponse> ingredients,

        @Schema(description = "레시피가 등록된 대회 정보")
        ContestResponse contest,

        @Schema(description = "소유자 여부")
        Boolean isOwner
) {
    public static RecipeResponse of(Recipe recipe, Boolean isOwner) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getThumbnailImage(),
                StepsResponse.of(recipe.getSteps()).steps(),
                IngredientsResponse.of(recipe.getIngredients()).ingredients(),
                ContestResponse.of(recipe.getContest()),
                isOwner
        );
    }
}
