package com.culinaryheaven.domain.recipe.controller;

import com.culinaryheaven.domain.recipe.dto.request.RecipeCreateRequest;
import com.culinaryheaven.domain.recipe.dto.response.RecipeResponse;
import com.culinaryheaven.domain.recipe.dto.response.RecipesResponse;
import com.culinaryheaven.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/{contestId}")
    public ResponseEntity<RecipesResponse> getAllRecipes(
            Pageable pageable,
            @PathVariable Long contestId

    ) {
        RecipesResponse recipesResponse = recipeService.findAllByContestId(pageable, contestId);
        return ResponseEntity.ok().body(recipesResponse);
    }


}
