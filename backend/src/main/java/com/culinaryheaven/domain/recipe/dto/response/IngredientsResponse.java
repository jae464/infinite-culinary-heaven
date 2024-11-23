package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.recipe.domain.Ingredient;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record IngredientsResponse(

        @Schema(description = "레시피 재료")
        List<IngredientResponse> ingredients

) {

    public static IngredientsResponse of(List<Ingredient> ingredients) {

        List<IngredientResponse> ingredientResponses = ingredients.stream()
                .map(IngredientResponse::of)
                .toList();

        return new IngredientsResponse(ingredientResponses);
    }
}
