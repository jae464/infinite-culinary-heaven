package com.culinaryheaven.domain.recipe.dto.request;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecipeCreateRequest(

        @NotNull
        @Schema(description = "레시피 제목", example = "휴게소 스타일의 알감자 요리입니다.")
        String title,

        @NotNull
        @Schema(description = "레시피 대표 이미지")
        String thumbnailImage,

        @NotNull
        @Schema(description = "진행중인 대회 ID")
        Long contestId,

        @NotNull
        @Schema(description = "레시피 진행 스텝")
        List<StepCreateRequest> steps
) {

    public Recipe toEntity(String storedThumbnailImageUrl, Contest contest) {
        return Recipe.builder()
                .title(title)
                .thumbnailImage(storedThumbnailImageUrl)
                .contest(contest)
                .build();
    }
}
