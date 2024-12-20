package com.culinaryheaven.domain.recipe.event;

import java.util.List;

public record RecipeLikeEvent(
        String recipeTitle,
        Long targetUserId,
        Long recipeId
) {
}
