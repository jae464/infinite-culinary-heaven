package com.culinaryheaven.domain.contest.dto.response;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import io.swagger.v3.oas.annotations.media.Schema;

public record TopicIngredientResponse(

        @Schema(description = "주재료 ID")
        Long id,

        @Schema(description = "주재료 이름")
        String name,

        @Schema(description = "주재료 이미지 URL")
        String image

) {
    public static TopicIngredientResponse of(TopicIngredient topicIngredient) {
        return new TopicIngredientResponse(
                topicIngredient.getId(),
                topicIngredient.getName(),
                topicIngredient.getImage()
        );
    }
}
