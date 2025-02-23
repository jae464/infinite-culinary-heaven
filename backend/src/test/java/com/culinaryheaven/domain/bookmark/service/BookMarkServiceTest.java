package com.culinaryheaven.domain.bookmark.service;

import com.culinaryheaven.domain.bookmark.domain.BookMark;
import com.culinaryheaven.domain.bookmark.dto.response.BookMarkResponse;
import com.culinaryheaven.domain.bookmark.repository.BookMarkRepository;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.culinaryheaven.global.util.SecurityUtil;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookMarkServiceTest {

    @Mock
    private BookMarkRepository bookMarkRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private BookMarkService bookMarkService;

    private FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .defaultNotNull(true)
                .build();
    }

    @Test
    void 북마크를_추가한다() {
        // Given
        Long recipeId = 1L;
        User user = fixtureMonkey.giveMeOne(User.class);
        Recipe recipe = fixtureMonkey.giveMeOne(Recipe.class);
        BookMark bookMark = fixtureMonkey.giveMeBuilder(BookMark.class)
                .set("recipe", recipe)
                .set("userId", user.getId())
                .sample();

        when(securityUtil.getUserOAuth2Id()).thenReturn("user-oauth-id");
        when(userRepository.findByOauthId("user-oauth-id")).thenReturn(Optional.of(user));
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        when(bookMarkRepository.existsByRecipeIdAndUserId(recipeId, user.getId())).thenReturn(false);
        when(bookMarkRepository.save(any(BookMark.class))).thenReturn(bookMark);

        // When
        BookMarkResponse response = bookMarkService.addBookMark(recipeId);

        // Then
        assertNotNull(response);
        assertEquals(bookMark.getId(), response.id());
        verify(bookMarkRepository).save(any(BookMark.class));
    }

    @Test
    void 북마크를_삭제한다() {
        // Given
        Long recipeId = 1L;
        User user = fixtureMonkey.giveMeOne(User.class);
        BookMark bookMark = fixtureMonkey.giveMeBuilder(BookMark.class)
                .set("recipe.id", recipeId)
                .set("userId", user.getId())
                .sample();

        when(userRepository.findByOauthId("user-oauth-id")).thenReturn(Optional.of(user));
        when(bookMarkRepository.findByRecipeIdAndUserId(recipeId, user.getId())).thenReturn(Optional.of(bookMark));

        // When
        bookMarkService.deleteBookMarkByRecipeId(recipeId, "user-oauth-id");

        // Then
        verify(bookMarkRepository).delete(bookMark);
    }

    @Test
    void 존재하지_않는_북마크_삭제시_예외를_발생시킨다() {
        // Given
        Long recipeId = 1L;
        User user = fixtureMonkey.giveMeOne(User.class);

        when(userRepository.findByOauthId("user-oauth-id")).thenReturn(Optional.of(user));
        when(bookMarkRepository.findByRecipeIdAndUserId(recipeId, user.getId())).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> bookMarkService.deleteBookMarkByRecipeId(recipeId, "user-oauth-id"));
        assertEquals(ErrorCode.BOOKMARK_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 나의_북마크_목록을_조회한다() {
        // Given
        Pageable pageable = Pageable.unpaged();
        User user = fixtureMonkey.giveMeOne(User.class);
        List<BookMark> bookMarks = fixtureMonkey.giveMe(BookMark.class, 3);
        Page<BookMark> bookMarkPage = new PageImpl<>(bookMarks);

        when(userRepository.findByOauthId("user-oauth-id")).thenReturn(Optional.of(user));
        when(bookMarkRepository.findAllByUserId(pageable, user.getId())).thenReturn(bookMarkPage);

        // When
        var response = bookMarkService.getAllBookMarks(pageable, "user-oauth-id");

        // Then
        assertNotNull(response);
        assertEquals(3, response.bookMarks().size());
        verify(bookMarkRepository).findAllByUserId(pageable, user.getId());
    }
}
