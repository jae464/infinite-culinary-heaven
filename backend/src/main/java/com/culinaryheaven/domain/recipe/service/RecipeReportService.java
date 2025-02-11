package com.culinaryheaven.domain.recipe.service;

import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.domain.RecipeReport;
import com.culinaryheaven.domain.recipe.dto.request.RecipeReportCreateRequest;
import com.culinaryheaven.domain.recipe.repository.RecipeReportRepository;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeReportService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeReportRepository recipeReportRepository;

    public void report(RecipeReportCreateRequest request, String oauth2Id ) {
        Recipe recipe = recipeRepository.findById(request.recipeId()).orElseThrow(
                () -> new CustomException(ErrorCode.RECIPE_NOT_FOUND)
        );
        User user = userRepository.findByOauthId(oauth2Id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));

        RecipeReport recipeReport = RecipeReport.builder()
                .recipe(recipe)
                .user(user)
                .reason(request.reason())
                .build();

        recipeReportRepository.save(recipeReport);
    }

}
