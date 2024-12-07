package com.culinaryheaven.domain.bookmark.dto.response;

import com.culinaryheaven.domain.bookmark.domain.BookMark;
import com.culinaryheaven.domain.recipe.dto.response.RecipePreviewResponse;
import com.culinaryheaven.domain.recipe.dto.response.RecipeResponse;
import io.swagger.v3.oas.annotations.media.Schema;

public record BookMarkResponse(

        @Schema(description = "북마크 ID")
        Long id,

        @Schema(description = "레시피 정보")
        RecipePreviewResponse recipe,

        @Schema(description = "유저 ID")
        Long userId

) {

    public static BookMarkResponse of(BookMark bookMark) {
        return new BookMarkResponse(bookMark.getId(), RecipePreviewResponse.of(bookMark.getRecipe()), bookMark.getUserId());
    }

}
