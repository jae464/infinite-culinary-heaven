package com.culinaryheaven.domain.recipe.service;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.domain.contest.repository.ContestRepository;
import com.culinaryheaven.domain.image.domain.ImageStorageClient;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.dto.request.IngredientCreateRequest;
import com.culinaryheaven.domain.recipe.dto.request.RecipeCreateRequest;
import com.culinaryheaven.domain.recipe.dto.request.StepCreateRequest;
import com.culinaryheaven.domain.recipe.dto.response.RecipeResponse;
import com.culinaryheaven.domain.recipe.repository.IngredientRepository;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import com.culinaryheaven.domain.recipe.repository.StepRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ContestRepository contestRepository;

    @Mock
    private ImageStorageClient imageStorageClient;

    @Mock
    private StepRepository stepRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock private SecurityContext securityContext;

    @Mock private Authentication authentication;

    @InjectMocks
    private RecipeService recipeService;

    private FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .defaultNotNull(true)
                .build();
    }

    @Test
    void 레시피를_생성한다() {
        // Given
        TopicIngredient topicIngredient = fixtureMonkey.giveMeOne(TopicIngredient.class);
        List<IngredientCreateRequest> ingredientCreateRequests = List.of(
                new IngredientCreateRequest("두부", "1모"),
                new IngredientCreateRequest("감자", "50g")
        );

        List<StepCreateRequest> stepCreateRequests = List.of(
                new StepCreateRequest(
                        1, "두부를 자른다", "image.jpg"
                ),
                new StepCreateRequest(
                        2, "감자를 자른다", "image.jpg"
                )
        );

        RecipeCreateRequest request = new RecipeCreateRequest(
                "알감자 요리",
                "휴게소 스타일의 간단한 요리",
                "thumbnail.jpg",
                1L,
                ingredientCreateRequests,
                stepCreateRequests
        );

        Contest contest = fixtureMonkey.giveMeBuilder(Contest.class)
                .set("id", 1L)
                .set("topicIngredient", topicIngredient)
                .sample();

        User user = fixtureMonkey.giveMeOne(User.class);
        Recipe recipe = fixtureMonkey.giveMeOne(Recipe.class);
        String thumbnailUrl = "http://example.com/thumbnail.jpg";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user-oauth-id");
        SecurityContextHolder.setContext(securityContext);

        when(contestRepository.findById(request.contestId())).thenReturn(Optional.of(contest));
        when(userRepository.findByOauthId("user-oauth-id")).thenReturn(Optional.of(user));
        when(imageStorageClient.uploadImage(any())).thenReturn(thumbnailUrl);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        // When
        RecipeResponse response = recipeService.create(request, List.of());

        // Then
        assertNotNull(response);
        assertEquals(recipe.getId(), response.id());
        verify(contestRepository).findById(request.contestId());
        verify(userRepository).findByOauthId("user-oauth-id");
        verify(recipeRepository).save(any(Recipe.class));
    }


    @Test
    void 존재하지_않는_레시피_조회시_예외를_발생시킨다() {
        // Given
        Long recipeId = 1L;

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> recipeService.getRecipeById(recipeId));
        assertEquals(ErrorCode.RECIPE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 특정_대회의_레시피를_조회한다() {
        // Given
        Long contestId = 1L;
        Pageable pageable = Pageable.unpaged();
        List<Recipe> recipes = fixtureMonkey.giveMe(Recipe.class, 3);
        Page<Recipe> recipePage = new PageImpl<>(recipes);
        when(recipeRepository.findAllByContestId(pageable, contestId)).thenReturn(recipePage);

        // When
        var response = recipeService.getRecipesByContestId(pageable, contestId);

        // Then
        assertNotNull(response);
        assertEquals(3, response.recipes().size());
        verify(recipeRepository).findAllByContestId(pageable, contestId);
    }

    @Test
    void 레시피를_삭제한다() {
        // Given
        Long recipeId = 1L;
        User user = fixtureMonkey.giveMeOne(User.class);
        Recipe recipe = fixtureMonkey.giveMeBuilder(Recipe.class)
                .set("user", user)
                .sample();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user-oauth-id");
        SecurityContextHolder.setContext(securityContext);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        when(userRepository.findByOauthId("user-oauth-id")).thenReturn(Optional.of(user));

        // When
        recipeService.deleteByRecipeId(recipeId);

        // Then
        verify(recipeRepository).delete(recipe);
    }

    @Test
    void 레시피_삭제시_권한이_없으면_예외를_발생시킨다() {
        // Given
        Long recipeId = 1L;
        User differentUser = fixtureMonkey.giveMeOne(User.class);
        Recipe recipe = fixtureMonkey.giveMeBuilder(Recipe.class)
                .set("user", differentUser)
                .sample();
        User currentUser = fixtureMonkey.giveMeOne(User.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user-oauth-id");
        SecurityContextHolder.setContext(securityContext);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        when(userRepository.findByOauthId("user-oauth-id")).thenReturn(Optional.of(currentUser));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> recipeService.deleteByRecipeId(recipeId));
        assertEquals(ErrorCode.AUTHORIZATION_FAILED, exception.getErrorCode());
    }
}
