package com.culinaryheaven.domain.contest.dto.request;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.global.annotation.EndTimeLimit;
import com.culinaryheaven.global.annotation.StartTimeLimit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ContestCreateRequest(

    @NotNull
    @Schema(description = "대회 이름", example = "두부 요리 대회")
    String name,

    @NotNull
    @Schema(description = "대회 설명", example = "창의로운 두부 요리를 뽐내봐요")
    String description,

    @NotNull
    @StartTimeLimit
    @Schema(description = "대회 시작 시간", example = "2024-11-18T00:00:00")
    LocalDateTime startDate,

    @NotNull
    @EndTimeLimit
    @Schema(description = "대회 종료 시간", example = "2024-11-25T23:59:59")
    LocalDateTime endDate,

    @NotNull
    @Schema(description = "주재료 아이디", example = "1")
    Long topicIngredientId
) {
    public Contest toEntity(TopicIngredient topicIngredient) {
        return Contest.builder()
                .description(description)
                .topicIngredient(topicIngredient)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
