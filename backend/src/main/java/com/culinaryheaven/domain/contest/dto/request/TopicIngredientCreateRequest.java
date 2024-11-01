package com.culinaryheaven.domain.contest.dto.request;

public record TopicIngredientCreateRequest(
        String name,
        String image
) {
    public static TopicIngredientCreateRequest of(String name, String image) {
        return new TopicIngredientCreateRequest(name, image);
    }
}
