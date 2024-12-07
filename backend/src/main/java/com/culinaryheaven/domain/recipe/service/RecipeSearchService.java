package com.culinaryheaven.domain.recipe.service;

import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.dto.response.RecipesResponse;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeSearchService {

    private final RecipeRepository recipeRepository;

    public RecipesResponse search(Pageable pageable, String keyword) {
        Page<Recipe> recipes = recipeRepository.findByTitleContainingIgnoreCase(pageable, keyword);
        return RecipesResponse.of(recipes);
    }

}
