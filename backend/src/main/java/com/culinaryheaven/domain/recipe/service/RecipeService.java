package com.culinaryheaven.domain.recipe.service;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.contest.repository.ContestRepository;
import com.culinaryheaven.domain.image.domain.ImageStorageClient;
import com.culinaryheaven.domain.recipe.domain.Ingredient;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.domain.Step;
import com.culinaryheaven.domain.recipe.dto.request.IngredientCreateRequest;
import com.culinaryheaven.domain.recipe.dto.request.RecipeCreateRequest;
import com.culinaryheaven.domain.recipe.dto.request.StepCreateRequest;
import com.culinaryheaven.domain.recipe.dto.response.RecipeResponse;
import com.culinaryheaven.domain.recipe.dto.response.RecipesResponse;
import com.culinaryheaven.domain.recipe.repository.IngredientRepository;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import com.culinaryheaven.domain.recipe.repository.StepRepository;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.culinaryheaven.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final ContestRepository contestRepository;
    private final ImageStorageClient imageStorageClient;
    private final StepRepository stepRepository;
    private final IngredientRepository ingredientRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public RecipeResponse create(
            RecipeCreateRequest request,
            List<MultipartFile> images
    ) {
        Contest contest = contestRepository.findById(request.contestId()).orElseThrow(() -> new IllegalArgumentException(
                "존재하지 않는 대회입니다."
        ));

        Map<String, MultipartFile> imageMap = images.stream()
                .collect(Collectors.toMap(MultipartFile::getOriginalFilename, file -> file));

        for (Map.Entry<String, MultipartFile> entry : imageMap.entrySet()) {
            System.out.println(entry);
            System.out.println(entry.getValue().getOriginalFilename());
        }

        System.out.println("Thumbnail Image from Request: " + request.thumbnailImage());
        System.out.println("Available Keys in Image Map: " + imageMap.keySet());

        MultipartFile thumbnailImageFile = imageMap.get(request.thumbnailImage());

        System.out.println(thumbnailImageFile);

        String thumbnailUrl = imageStorageClient.uploadImage(imageMap.get(request.thumbnailImage()));

        User user = userRepository.findByOauthId(securityUtil.getUserOAuth2Id()).orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));
        Recipe recipe = request.toEntity(user, thumbnailUrl, contest);

        Recipe savedRecipe = recipeRepository.save(recipe);

        for (IngredientCreateRequest ingredientCreateRequest : request.ingredients()) {
            Ingredient ingredient = ingredientCreateRequest.toEntity(savedRecipe);
            Ingredient savedIngredient = ingredientRepository.save(ingredient);
            recipe.getIngredients().add(savedIngredient);
        }

        for (StepCreateRequest stepRequest : request.steps()) {
            String stepImageUrl = imageStorageClient.uploadImage(imageMap.get(stepRequest.imageUrl()));

            Step step = stepRequest.toEntity(stepImageUrl, savedRecipe);
            Step savedStep = stepRepository.save(step);
            recipe.getSteps().add(savedStep);
        }

        return RecipeResponse.of(savedRecipe, false, false, true);
    }

    public RecipeResponse getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
        User currentUser = userRepository.findByOauthId(securityUtil.getUserOAuth2Id())
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));
        Boolean isOwner = recipe.getUser().getId().equals(currentUser.getId());
        Boolean isBookMarked = recipe.getBookmarks().stream()
                .anyMatch(bookmark -> bookmark.getUserId().equals(currentUser.getId()));
        Boolean isLiked = recipe.getLikes().stream()
                .anyMatch(like -> like.getUser().getId().equals(currentUser.getId()));
        return RecipeResponse.of(recipe, isBookMarked, isLiked, isOwner);
    }

    public RecipesResponse getAllRecipes(Pageable pageable) {
        Page<Recipe> recipes = recipeRepository.findAll(pageable);
        return RecipesResponse.of(recipes);
    }

    public RecipesResponse getRecipesByContestId(Pageable pageable, Long id) {
        Page<Recipe> recipes = recipeRepository.findAllByContestId(pageable, id);
        return RecipesResponse.of(recipes);
    }

    @Transactional
    public void deleteByRecipeId(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
        User user = userRepository.findByOauthId(securityUtil.getUserOAuth2Id())
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));

        if (!recipe.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.AUTHORIZATION_FAILED);
        }

        recipeRepository.delete(recipe);
    }


}
