package com.culinaryheaven.domain.comment.dto.request;

import com.culinaryheaven.domain.comment.domain.Comment;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record CommentCreateRequest(

        @Schema(description = "레시피 ID")
        Long recipeId,

        @Schema(description = "댓글 내용")
        String content

) {
        public Comment toEntity(User user, Recipe recipe) {
                return Comment.builder()
                        .user(user)
                        .recipe(recipe)
                        .content(content)
                        .build();
        }
}
