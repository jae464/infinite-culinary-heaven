package com.culinaryheaven.domain.comment.service;

import com.culinaryheaven.domain.comment.domain.Comment;
import com.culinaryheaven.domain.comment.dto.request.CommentCreateRequest;
import com.culinaryheaven.domain.comment.dto.response.CommentResponse;
import com.culinaryheaven.domain.comment.dto.response.CommentsResponse;
import com.culinaryheaven.domain.comment.repository.CommentRepository;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.culinaryheaven.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public CommentResponse createComment(CommentCreateRequest request) {

        User user = userRepository.findByOauthId(
                securityUtil.getUserOAuth2Id()
        ).orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));

        Recipe recipe = recipeRepository.findById(request.recipeId()).orElseThrow(
                () -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        Comment comment = request.toEntity(user, recipe);
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.of(savedComment);

    }

    public CommentsResponse getCommentsByRecipeId(Long recipeId) {

        List<Comment> comments = commentRepository.findByRecipeId(
                Sort.by(Sort.Direction.DESC, "createdAt"), recipeId
        );

        return CommentsResponse.of(comments);

    }

}
