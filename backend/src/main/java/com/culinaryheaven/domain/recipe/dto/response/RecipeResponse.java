package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.contest.dto.response.ContestResponse;
import com.culinaryheaven.domain.recipe.domain.Recipe;

public record RecipeResponse(
        String title,
        String thumbnailImage,
        ContestResponse contest
) {
    public static RecipeResponse of(Recipe recipe) {
        return new RecipeResponse(recipe.getTitle(), recipe.getThumbnailImage(), ContestResponse.of(recipe.getContest()));
    }
}
