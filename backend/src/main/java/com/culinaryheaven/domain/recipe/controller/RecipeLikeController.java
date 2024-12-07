package com.culinaryheaven.domain.recipe.controller;

import com.culinaryheaven.domain.recipe.dto.response.RecipeLikeResponse;
import com.culinaryheaven.domain.recipe.service.RecipeLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeLikeController {

    private final RecipeLikeService recipeLikeService;

    @PostMapping("/like/{recipeId}")
    public ResponseEntity<RecipeLikeResponse> create(
            @PathVariable Long recipeId
    ) {
        RecipeLikeResponse recipeLikeResponse = recipeLikeService.likeRecipe(recipeId);
        return ResponseEntity.ok(recipeLikeResponse);
    }

    @DeleteMapping("/unlike/{recipeId}")
    public ResponseEntity<RecipeLikeResponse> delete(
            @PathVariable Long recipeId
    ) {
        recipeLikeService.unlikeRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

}
