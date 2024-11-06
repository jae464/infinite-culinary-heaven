package com.culinaryheaven.domain.recipe.dto.request;

public record RecipeCreateRequest(
        String title,
        String thumbnailImage,
        Long contestId
) {

}
