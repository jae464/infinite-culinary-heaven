package com.culinaryheaven.domain.recipe.dto.request;

import com.sun.jdi.request.StepRequest;

import java.util.List;

public record RecipeCreateRequest(
        String title,
        String thumbnailImage,
        Long contestId,
        List<StepRequest> steps
) {
    public record StepRequest(
            String description,
            String ImageUrl
    ) {}
}
