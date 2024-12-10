package com.culinaryheaven.domain.comment.dto.response;

import com.culinaryheaven.domain.comment.domain.Comment;
import com.culinaryheaven.domain.user.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CommentResponse(

        @Schema(description = "댓글 ID")
        Long id,

        @Schema(description = "댓글 내용")
        String content,

        @Schema(description = "댓글 작성자")
        UserInfoResponse userInfo,

        @Schema(description = "댓글 작성 시간")
        LocalDateTime createdAt
) {

        public static CommentResponse of(Comment comment) {
                return new CommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        UserInfoResponse.of(comment.getUser()),
                        comment.getCreatedAt()
                );
        }

}
