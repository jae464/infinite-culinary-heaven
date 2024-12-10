package com.culinaryheaven.domain.comment.controller;

import com.culinaryheaven.domain.comment.dto.request.CommentCreateRequest;
import com.culinaryheaven.domain.comment.dto.response.CommentResponse;
import com.culinaryheaven.domain.comment.dto.response.CommentsResponse;
import com.culinaryheaven.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(
        @RequestBody CommentCreateRequest request
    ) {
        CommentResponse commentResponse = commentService.createComment(request);
        return ResponseEntity.ok().body(commentResponse);
    }

    @GetMapping
    public ResponseEntity<CommentsResponse> getCommentsByRecipeId(
        @RequestParam Long recipeId
    ) {
        CommentsResponse commentsResponse = commentService.getCommentsByRecipeId(recipeId);
        return ResponseEntity.ok().body(commentsResponse);
    }

}
