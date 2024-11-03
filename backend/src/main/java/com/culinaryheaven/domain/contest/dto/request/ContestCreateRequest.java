package com.culinaryheaven.domain.contest.dto.request;

import com.culinaryheaven.global.annotation.StartTimeLimit;

import java.time.LocalDateTime;

public record ContestCreateRequest(
    String name,
    String description,
    @StartTimeLimit
    LocalDateTime startDate,
    Long topicIngredientId
) {

}
