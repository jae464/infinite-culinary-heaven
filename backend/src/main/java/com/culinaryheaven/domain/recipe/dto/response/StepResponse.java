package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.recipe.domain.Step;
import io.swagger.v3.oas.annotations.media.Schema;

public record StepResponse(

        @Schema(description = "레시피 단계 ID")
        Long id,

        @Schema(description = "레시피 단계 설명")
        String description,

        @Schema(description = "레시피 단계 이미지 URL")
        String imageUrl

) {

        public static StepResponse of(Step step) {
                return new StepResponse(step.getId(), step.getDescription(), step.getImage());
        }
}
