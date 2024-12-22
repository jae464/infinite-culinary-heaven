package com.culinaryheaven.domain.recipe.dto.request;

import com.culinaryheaven.domain.recipe.domain.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecipeUpdateRequest(
        @NotNull
        @Schema(description = "레시피 제목", example = "휴게소 스타일의 알감자 요리입니다.")
        String title,

        @NotNull
        @Schema(description = "레시피 설명")
        String description,

        @NotNull
        @Schema(description = "레시피 대표 이미지")
        String thumbnailImage,

        @NotNull
        @Schema(description = "요리에 사용되는 재료")
        List<IngredientUpdateRequest> ingredients,

        @NotNull
        @Schema(description = "레시피 진행 스텝")
        List<StepUpdateRequest> steps
) {
}
