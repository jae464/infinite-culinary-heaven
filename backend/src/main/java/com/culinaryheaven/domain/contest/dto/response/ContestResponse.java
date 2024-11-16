package com.culinaryheaven.domain.contest.dto.response;

import com.culinaryheaven.domain.contest.domain.Contest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ContestResponse(

        @Schema(description = "대회 ID", example = "1")
        Long id,

        @Schema(description = "대회 설명", example = "창의로운 두부 요리를 뽐내봐요")
        String description,

        @Schema(description = "대회 시작 시간", example = "2024-11-18T00:00:00")
        LocalDateTime startDate,

        @Schema(description = "대회 종료 시간", example = "2024-11-25T23:59:59")
        LocalDateTime endDate,

        @Schema(description = "주재료 정보", example =
                """                        
                        {
                            "id": 1,
                            "name": "두부",
                            "image": "https://image.url.com/tofu"                           
                        }    
                        
                        """)
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
