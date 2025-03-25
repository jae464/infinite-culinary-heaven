package com.culinaryheaven.domain.comment.service;

import com.culinaryheaven.domain.comment.domain.Comment;
import com.culinaryheaven.domain.comment.dto.request.CommentCreateRequest;
import com.culinaryheaven.domain.comment.dto.request.CommentUpdateRequest;
import com.culinaryheaven.domain.comment.dto.response.CommentResponse;
import com.culinaryheaven.domain.comment.repository.CommentRepository;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private CommentService commentService;

    private FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .defaultNotNull(true)
                .build();
    }

    @Test
    void 댓글을_생성한다() {
        // Given
        User user = fixtureMonkey.giveMeOne(User.class);
        Recipe recipe = fixtureMonkey.giveMeOne(Recipe.class);
        CommentCreateRequest request = new CommentCreateRequest(recipe.getId(), "Test Comment");
        Comment comment = request.toEntity(user, recipe);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(recipeRepository.findById(recipe.getId())).thenReturn(Optional.of(recipe));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // When
        CommentResponse response = commentService.createComment(request, user.getId());

        // Then
        assertNotNull(response);
        assertEquals(request.content(), response.content());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void 존재하지_않는_레시피에_댓글을_생성하면_예외를_발생시킨다() {
        // Given
        Long recipeId = 1L;
        User user = fixtureMonkey.giveMeOne(User.class);
        CommentCreateRequest request = new CommentCreateRequest(recipeId, "Test Comment");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> commentService.createComment(request, 1L));
        assertEquals(ErrorCode.RECIPE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 댓글을_수정한다() {
        // Given
        Long commentId = 1L;
        User user = fixtureMonkey.giveMeOne(User.class);
        Comment comment = fixtureMonkey.giveMeBuilder(Comment.class)
                .set("id", commentId)
                .set("user", user)
                .sample();
        CommentUpdateRequest request = new CommentUpdateRequest("Updated Comment");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // When
        CommentResponse response = commentService.updateCommentById(commentId, request, 1L);

        // Then
        assertNotNull(response);
        assertEquals(request.content(), response.content());
        verify(commentRepository).findById(commentId);
    }

    @Test
    void 존재하지_않는_댓글을_수정하면_예외를_발생시킨다() {
        // Given
        Long commentId = 1L;
        User user = fixtureMonkey.giveMeOne(User.class);
        CommentUpdateRequest request = new CommentUpdateRequest("Updated Comment");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> commentService.updateCommentById(commentId, request, user.getId()));
        assertEquals(ErrorCode.COMMENT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 댓글을_삭제한다() {
        // Given
        Long commentId = 1L;
        User user = fixtureMonkey.giveMeOne(User.class);
        Comment comment = fixtureMonkey.giveMeBuilder(Comment.class)
                .set("id", commentId)
                .set("user", user)
                .sample();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // When
        commentService.deleteCommentById(commentId, user.getId());

        // Then
        verify(commentRepository).delete(comment);
    }

    @Test
    void 댓글_삭제_시_권한이_없으면_예외를_발생시킨다() {
        // Given
        Long commentId = 1L;
        User user = fixtureMonkey.giveMeOne(User.class);
        User anotherUser = fixtureMonkey.giveMeOne(User.class);

        Comment comment = fixtureMonkey.giveMeBuilder(Comment.class)
                .set("id", commentId)
                .set("user", anotherUser)
                .sample();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> commentService.deleteCommentById(commentId, user.getId()));
        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());
    }
}
