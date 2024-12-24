package com.culinaryheaven.domain.comment.event;

public record CommentEvent(
        String recipeTitle,
        String comment,
        Long targetUserId,
        Long recipeId
) {
}
