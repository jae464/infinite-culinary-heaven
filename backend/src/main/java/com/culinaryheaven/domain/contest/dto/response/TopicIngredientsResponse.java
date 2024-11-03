package com.culinaryheaven.domain.contest.dto.response;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import org.springframework.data.domain.Slice;

import java.util.List;

public record TopicIngredientsResponse(
        List<TopicIngredientResponse> topicIngredients
) {
    public static TopicIngredientsResponse of(final Slice<TopicIngredient> topicIngredients) {
        final List<TopicIngredientResponse> topicIngredientResponses = topicIngredients.stream()
                .map(TopicIngredientResponse::of)
                .toList();

        return new TopicIngredientsResponse(topicIngredientResponses);
    }
}
