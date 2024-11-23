package com.culinaryheaven.domain.recipe.dto.request;

import com.culinaryheaven.domain.recipe.domain.Ingredient;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record IngredientCreateRequest(

        @NotNull
        @Schema(description = "재료 이름")
        String name,

        @NotNull
        @Schema(description = "재료 양")
        String quantity

) {

        public Ingredient toEntity(Recipe recipe) {
                return Ingredient.builder()
                        .name(name)
                        .quantity(quantity)
                        .recipe(recipe)
                        .build();
        }

}
