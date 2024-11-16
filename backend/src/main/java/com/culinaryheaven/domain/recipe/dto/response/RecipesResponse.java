package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.recipe.domain.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Slice;

import java.util.List;

public record RecipesResponse(

        @Schema(description = "레시피 정보")
        List<RecipeResponse> recipes

) {
    public static RecipesResponse of(Slice<Recipe> recipes) {
        List<RecipeResponse> recipeResponses = recipes.getContent()
                .stream()
                .map(RecipeResponse::of)
                .toList();

        return new RecipesResponse(recipeResponses);
    }
}
