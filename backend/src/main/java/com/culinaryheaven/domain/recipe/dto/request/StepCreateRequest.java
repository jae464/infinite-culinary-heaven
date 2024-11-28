package com.culinaryheaven.domain.recipe.dto.request;

import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.domain.Step;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record StepCreateRequest(

        @NotNull
        @Schema(description = "레시피 단계 설명")
        String description,

        // todo 지우기
        @Schema(description = "레시피 단계 이미지 URL")
        String imageUrl

) {
        public Step toEntity(String storedStepImageUrl, Recipe recipe) {
                return Step.builder()
                        .description(description)
                        .image(storedStepImageUrl)
                        .recipe(recipe)
                        .build();
        }
}
