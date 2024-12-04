package com.culinaryheaven.domain.recipe.controller;

import com.culinaryheaven.domain.recipe.dto.request.RecipeCreateRequest;
import com.culinaryheaven.domain.recipe.dto.response.RecipeResponse;
import com.culinaryheaven.domain.recipe.dto.response.RecipesResponse;
import com.culinaryheaven.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<RecipeResponse> create(
            @RequestPart List<MultipartFile> images,
            @RequestPart RecipeCreateRequest request
    ) {
        RecipeResponse recipeResponse = recipeService.create(request, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeResponse);
    }

    @GetMapping
    public ResponseEntity<RecipesResponse> getAllRecipes(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) Long contestId
    ) {
        RecipesResponse recipesResponse;
        if (contestId == null) {
            recipesResponse = recipeService.getAllRecipes(pageable);
        }
        else {
            recipesResponse = recipeService.getRecipesByContestId(pageable, contestId);
        }
        return ResponseEntity.ok().body(recipesResponse);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeResponse> getRecipe(
        @PathVariable Long recipeId
    ) {
        RecipeResponse recipeResponse = recipeService.getRecipeById(recipeId);
        return ResponseEntity.ok().body(recipeResponse);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteByRecipeId(
            @PathVariable Long recipeId
    ) {
        recipeService.deleteByRecipeId(recipeId);
        return ResponseEntity.noContent().build();
    }

}
