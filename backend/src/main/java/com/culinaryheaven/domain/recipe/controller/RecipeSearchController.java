package com.culinaryheaven.domain.recipe.controller;

import com.culinaryheaven.domain.recipe.dto.response.RecipesResponse;
import com.culinaryheaven.domain.recipe.service.RecipeSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/recipes")
@RequiredArgsConstructor
public class RecipeSearchController {

    private final RecipeSearchService recipeSearchService;

    @GetMapping
    public ResponseEntity<RecipesResponse> search(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String keyword
    ) {
        RecipesResponse recipesResponse = recipeSearchService.search(pageable, keyword);
        return ResponseEntity.ok().body(recipesResponse);
    }

}
