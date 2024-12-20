package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.contest.dto.response.ContestResponse;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RecipeResponse(

        @Schema(description = "레시피 ID")
        Long id,

        @Schema(description = "레시피 제목")
        String title,

        @Schema(description = "레시피 설명")
        String description,

        @Schema(description = "레시피 대표 이미지 URL")
        String thumbnailImageUrl,

        @Schema(description = "소유자 정보")
        WriterInfoResponse writerInfoResponse,

        @Schema(description = "레시피 스텝")
        List<StepResponse> steps,

        @Schema(description = "사용된 재료")
        List<IngredientResponse> ingredients,

        @Schema(description = "레시피가 등록된 대회 정보")
        ContestResponse contest,

        @Schema(description = "북마크 개수")
        int bookMarkCounts,

        @Schema(description = "좋아요 개수")
        int likeCounts,

        @Schema(description = "북마크 여부")
        Boolean isBookMarked,

        @Schema(description = "좋아요 여부")
        Boolean isLiked,

        @Schema(description = "소유자 여부")
        Boolean isOwner
) {
    public static RecipeResponse of(Recipe recipe, Boolean isBookMarked, Boolean isLiked, Boolean isOwner) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getThumbnailImage(),
                WriterInfoResponse.of(recipe.getUser()),
                StepsResponse.of(recipe.getSteps()).steps(),
                IngredientsResponse.of(recipe.getIngredients()).ingredients(),
                ContestResponse.of(recipe.getContest()),
                recipe.getBookMarkCount(),
                recipe.getLikeCount(),
                isBookMarked,
                isLiked,
                isOwner
        );
    }
}
