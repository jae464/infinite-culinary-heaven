package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.recipe.domain.RecipeLike;
import com.culinaryheaven.domain.user.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

public record RecipeLikeResponse(

        @Schema(description = "레시피 좋아요 ID")
        Long id,

        @Schema(description = "레시피 정보")
        RecipeResponse recipe,

        @Schema(description = "유저 정보")
        UserInfoResponse user

) {

    public static RecipeLikeResponse of(RecipeLike recipeLike) {
        return new RecipeLikeResponse(recipeLike.getId(), RecipeResponse.of(recipeLike.getRecipe(), false), UserInfoResponse.of(recipeLike.getUser()));
    }

}
