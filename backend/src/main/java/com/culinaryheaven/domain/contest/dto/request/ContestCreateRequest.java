package com.culinaryheaven.domain.contest.dto.request;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.global.annotation.EndTimeLimit;
import com.culinaryheaven.global.annotation.StartTimeLimit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ContestCreateRequest(

    @NotNull
    @Schema(description = "대회 이름", example = "두부 요리 대회")
    String name,

    @NotNull
    @Schema(description = "대회 설명", example = "창의로운 두부 요리를 뽐내봐요")
    String description,

    @NotNull
    @Schema(description = "대회 시작 날짜", example = "2024-11-18")
    LocalDate startDate,

    @Schema(description = "대회 시작 시간", example = "00:00:00")
    LocalTime startTime,

    @NotNull
    @Schema(description = "대회 종료 날짜", example = "2024-11-25")
    LocalDate endDate,

    @Schema(description = "대회 종료 시간", example = "11:59:59")
    LocalTime endTime,

    @NotNull
    @Schema(description = "주재료 아이디", example = "1")
    Long topicIngredientId
) {
    public Contest toEntity(TopicIngredient topicIngredient) {
        LocalDateTime startDateTime = LocalDateTime.of(
                startDate,
                startTime != null ? startTime : LocalTime.MIDNIGHT
        );

        LocalDateTime endDateTime = LocalDateTime.of(
                endDate,
                endTime != null ? endTime : LocalTime.of(23, 59, 59)
        );

        return Contest.builder()
                .name(name)
                .description(description)
                .topicIngredient(topicIngredient)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .build();
    }
}
