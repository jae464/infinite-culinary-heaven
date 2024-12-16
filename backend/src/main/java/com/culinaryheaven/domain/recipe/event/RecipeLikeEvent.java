package com.culinaryheaven.domain.recipe.event;

public record RecipeLikeEvent(
        String recipeTitle,
        Long targetUserId
) {
}
