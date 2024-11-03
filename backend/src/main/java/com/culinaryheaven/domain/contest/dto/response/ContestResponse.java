package com.culinaryheaven.domain.contest.dto.response;

import com.culinaryheaven.domain.contest.domain.Contest;

import java.time.LocalDateTime;

public record ContestResponse(
        Long id,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        TopicIngredientResponse topicIngredient
) {
    public static ContestResponse of(Contest contest) {
        return new ContestResponse(
                contest.getId(),
                contest.getDescription(),
                contest.getStartDate(),
                contest.getEndDate(),
                TopicIngredientResponse.of(contest.getTopicIngredient())
        );
    }
}
