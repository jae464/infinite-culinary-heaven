package com.culinaryheaven.domain.recipe.controller;

import com.culinaryheaven.domain.recipe.dto.request.RecipeCreateRequest;
import com.culinaryheaven.domain.recipe.dto.request.RecipeUpdateRequest;
import com.culinaryheaven.domain.recipe.dto.response.RecipeResponse;
import com.culinaryheaven.domain.recipe.dto.response.RecipesResponse;
import com.culinaryheaven.domain.recipe.service.RecipeService;
import com.culinaryheaven.global.annotation.Authenticated;
import com.culinaryheaven.global.security.PrincipalUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<RecipeResponse> create(
            @Authenticated PrincipalUserInfo userInfo,
            @RequestPart List<MultipartFile> images,
            @RequestPart RecipeCreateRequest request
    ) {
        RecipeResponse recipeResponse = recipeService.create(request, images, userInfo.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeResponse);
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, path = "/{recipeId}")
    public ResponseEntity<RecipeResponse> update(
            @Authenticated PrincipalUserInfo userInfo,
            @RequestPart(required = false) List<MultipartFile> images,
            @RequestPart RecipeUpdateRequest request,
            @PathVariable Long recipeId) {
        if (images == null) {
            images = Collections.emptyList();
        }
        RecipeResponse recipeResponse = recipeService.updateRecipe(recipeId, request, images, userInfo.userId());
        return ResponseEntity.ok().body(recipeResponse);
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

    @GetMapping("/mine")
    public ResponseEntity<RecipesResponse> getMyRecipes(
            @Authenticated PrincipalUserInfo userInfo,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        RecipesResponse recipesResponse = recipeService.getMyRecipes(pageable, userInfo.userId());
        return ResponseEntity.ok().body(recipesResponse);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeResponse> getRecipe(
        @Authenticated(required = false) PrincipalUserInfo userInfo,
        @PathVariable Long recipeId
    ) {
        RecipeResponse recipeResponse = recipeService.getRecipeById(recipeId, userInfo.userId());
        return ResponseEntity.ok().body(recipeResponse);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteByRecipeId(
            @Authenticated PrincipalUserInfo userInfo,
            @PathVariable Long recipeId
    ) {
        recipeService.deleteByRecipeId(recipeId, userInfo.userId());
        return ResponseEntity.noContent().build();
    }

}
