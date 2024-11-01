package com.culinaryheaven.domain.contest.dto.request;

public record ContestCreateRequest(
    String name,
    String description,
    Long topicIngredientId
) {
    public static ContestCreateRequest of(String name, String description, Long topicIngredientId) {
        return new ContestCreateRequest(name, description, topicIngredientId);
    }
}
