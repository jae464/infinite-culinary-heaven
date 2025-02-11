package com.culinaryheaven.domain.recipe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecipeReportCreateRequest(
        @Schema(description = "레시피 ID")
        Long recipeId,
        @Schema(description = "신고 이유")
        String reason
) {
}
