package com.culinaryheaven.domain.recipe.service;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.contest.repository.ContestRepository;
import com.culinaryheaven.domain.image.domain.ImageStorageClient;
import com.culinaryheaven.domain.recipe.domain.Ingredient;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.domain.Step;
import com.culinaryheaven.domain.recipe.dto.request.*;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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
            List<MultipartFile> images,
            String oauth2Id
    ) {
        Contest contest = contestRepository.findById(request.contestId()).orElseThrow(() -> new CustomException(ErrorCode.CONTEST_NOT_FOUND));

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

//        User user = userRepository.findByOauthId(securityUtil.getUserOAuth2Id()).orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));
        User user = userRepository.findByOauthId(oauth2Id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));
        Recipe recipe = request.toEntity(user, thumbnailUrl, contest);

        Recipe savedRecipe = recipeRepository.save(recipe);

        for (IngredientCreateRequest ingredientCreateRequest : request.ingredients()) {
            Ingredient ingredient = ingredientCreateRequest.toEntity(savedRecipe);
            Ingredient savedIngredient = ingredientRepository.save(ingredient);
            recipe.getIngredients().add(savedIngredient);
        }

        for (StepCreateRequest stepRequest : request.steps()) {

            String stepImageUrl = imageStorageClient.uploadImage(imageMap.get(stepRequest.imageName()));
            Step step = stepRequest.toEntity(stepImageUrl, savedRecipe);
            Step savedStep = stepRepository.save(step);
            recipe.getSteps().add(savedStep);
        }

        return RecipeResponse.of(savedRecipe, false, false, true);
    }

    @Transactional
    public RecipeResponse updateRecipe(
            Long recipeId,
            RecipeUpdateRequest request,
            List<MultipartFile> images,
            String oauth2Id
    ) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        if (!recipe.getUser().getOauthId().equals(oauth2Id)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        recipe.updateTitle(request.title());
        recipe.updateDescription(request.description());

        Map<String, MultipartFile> imageMap = images.stream()
                .collect(Collectors.toMap(MultipartFile::getOriginalFilename, file -> file));

        MultipartFile thumbnailImageFile = imageMap.get(request.thumbnailImage());

        // check thumbnail changed
        if (thumbnailImageFile != null) {
            log.info("썸네일 이미지 변경됨. 새로 저장");
            String thumbnailUrl = imageStorageClient.uploadImage(thumbnailImageFile);
            recipe.updateThumbnailImage(thumbnailUrl);
        }

        // clear previous steps
        recipe.getSteps().clear();

        // clear previous ingredients
        recipe.getIngredients().clear();

        for (IngredientUpdateRequest ingredientRequest : request.ingredients()) {
            Ingredient ingredient = ingredientRequest.toEntity(recipe);
            Ingredient savedIngredient = ingredientRepository.save(ingredient);
            recipe.getIngredients().add(savedIngredient);
        }

        for (StepUpdateRequest stepRequest : request.steps()) {
            // use previous image url
            String stepImageUrl;
            if (stepRequest.imageUrl() != null) {
                stepImageUrl = stepRequest.imageUrl();
            }
            else {
                stepImageUrl = imageStorageClient.uploadImage(imageMap.get(stepRequest.imageName()));
            }
            Step step = stepRequest.toEntity(stepImageUrl, recipe);
            Step savedStep = stepRepository.save(step);
            recipe.getSteps().add(savedStep);
        }

        return RecipeResponse.of(recipe, false, false, true);
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

    public RecipesResponse getMyRecipes(Pageable pageable, String oauth2Id) {
        User user = userRepository.findByOauthId(oauth2Id)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));

        Page<Recipe> recipes = recipeRepository.findAllByUserId(pageable, user.getId());
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
