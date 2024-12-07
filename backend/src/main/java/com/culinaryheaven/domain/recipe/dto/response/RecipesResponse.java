package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.recipe.domain.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

public record RecipesResponse(

        @Schema(description = "레시피 정보")
        List<RecipeResponse> recipes

) {
    public static RecipesResponse of(Page<Recipe> recipes) {
        List<RecipeResponse> recipeResponses = recipes.getContent()
                .stream()
                .map(recipe -> RecipeResponse.of(recipe, null))
                .toList();

        return new RecipesResponse(recipeResponses);
    }

    public static RecipesResponse of(List<Recipe> recipes) {
        List<RecipeResponse> recipeResponses = recipes
                .stream()
                .map(recipe -> RecipeResponse.of(recipe, null))
                .toList();

        return new RecipesResponse(recipeResponses);
    }
}
