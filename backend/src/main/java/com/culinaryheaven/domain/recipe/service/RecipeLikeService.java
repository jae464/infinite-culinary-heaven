package com.culinaryheaven.domain.recipe.service;

import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.domain.RecipeLike;
import com.culinaryheaven.domain.recipe.dto.response.RecipeLikeResponse;
import com.culinaryheaven.domain.recipe.repository.RecipeLikeRepository;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.culinaryheaven.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeLikeService {

    private final RecipeLikeRepository recipeLikeRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final RecipeRepository recipeRepository;

    public RecipeLikeResponse likeRecipe(Long recipeId) {
        User user = userRepository.findByOauthId(securityUtil.getUserOAuth2Id())
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));

        validateRecipeLike(recipeId, user.getId());

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        RecipeLike recipeLike = RecipeLike.builder()
                .recipe(recipe)
                .user(user)
                .build();

        RecipeLike savedRecipeLike = recipeLikeRepository.save(recipeLike);

        return RecipeLikeResponse.of(savedRecipeLike);
    }

    public void unlikeRecipe(Long recipeId) {
        User user = userRepository.findByOauthId(securityUtil.getUserOAuth2Id())
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));

        RecipeLike recipeLike = recipeLikeRepository.findByRecipeIdAndUserId(recipeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_LIKE_NOT_FOUND));

        validateRecipeLikeOwner(recipeLike, user);

        recipeLikeRepository.delete(recipeLike);
    }

    private void validateRecipeLike(Long recipeId, Long userId) {
        boolean exists = recipeLikeRepository.existsByRecipeIdAndUserId(recipeId, userId);

        if (exists) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_RECIPE_LIKE);
        }
    }

    private void validateRecipeLikeOwner(RecipeLike recipeLike, User user) {
        if (!recipeLike.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

}
