package com.culinaryheaven.domain.contest.dto.response;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;

public record TopicIngredientResponse(
        Long id,
        String name,
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
