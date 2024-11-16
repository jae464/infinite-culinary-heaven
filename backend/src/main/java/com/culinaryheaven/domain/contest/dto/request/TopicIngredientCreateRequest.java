package com.culinaryheaven.domain.contest.dto.request;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record TopicIngredientCreateRequest(

        @NotNull
        @Schema(description = "주재료 이름", example = "두부")
        String name,

        @NotNull
        @Schema(description = "주재료 이미지 주소")
        String image
) {
    public TopicIngredient toEntity() {
        return TopicIngredient.builder()
                .name(name)
                .image(image)
                .build();
    }
}
