package com.culinaryheaven.domain.comment.dto.response;

import com.culinaryheaven.domain.comment.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public record CommentsResponse(

        @Schema(description = "댓글 정보")
        List<CommentResponse> comments

) {

    public static CommentsResponse of(List<Comment> comments) {
        List<CommentResponse> commentResponses = comments
                .stream()
                .map(CommentResponse::of)
                .toList();

        return new CommentsResponse(commentResponses);
    }

}
