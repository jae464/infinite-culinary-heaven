package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.recipe.domain.Ingredient;
import io.swagger.v3.oas.annotations.media.Schema;

public record IngredientResponse(

        @Schema(description = "재료 ID")
        long id,

        @Schema(description = "재료 이름")
        String name,

        @Schema(description = "재료 양")
        String quantity

) {
        public static IngredientResponse of(Ingredient ingredient) {
                return new IngredientResponse(ingredient.getId(), ingredient.getName(), ingredient.getQuantity());
        }
}
