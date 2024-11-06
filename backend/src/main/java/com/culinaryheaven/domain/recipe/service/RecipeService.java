package com.culinaryheaven.domain.recipe.service;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.contest.repository.ContestRepository;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.dto.request.RecipeCreateRequest;
import com.culinaryheaven.domain.recipe.dto.response.RecipeResponse;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ContestRepository contestRepository;

    public RecipeResponse create(RecipeCreateRequest request) {
        Contest contest = contestRepository.findById(request.contestId()).orElseThrow(() -> new IllegalArgumentException(
                "존재하지 않는 대회입니다."
        ));

        Recipe recipe = Recipe.builder()
                .title(request.title())
                .thumbnailImage(request.thumbnailImage())
                .contest(contest)
                .build();

        Recipe savedRecipe = recipeRepository.save(recipe);
        return RecipeResponse.of(savedRecipe);
    }

}
