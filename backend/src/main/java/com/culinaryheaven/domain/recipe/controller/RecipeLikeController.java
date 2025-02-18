package com.culinaryheaven.domain.recipe.controller;

import com.culinaryheaven.domain.recipe.dto.response.RecipeLikeResponse;
import com.culinaryheaven.domain.recipe.dto.response.RecipeLikesResponse;
import com.culinaryheaven.domain.recipe.service.RecipeLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes/likes")
@RequiredArgsConstructor
public class RecipeLikeController {

    private final RecipeLikeService recipeLikeService;

    @PostMapping("/{recipeId}")
    public ResponseEntity<RecipeLikeResponse> create(
            @PathVariable Long recipeId
    ) {
        RecipeLikeResponse recipeLikeResponse = recipeLikeService.likeRecipe(recipeId);
        return ResponseEntity.ok().body(recipeLikeResponse);
    }

    @GetMapping("/mine")
    public ResponseEntity<RecipeLikesResponse> getMyLikes(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        RecipeLikesResponse recipeLikesResponse = recipeLikeService.getMyRecipeLikes(pageable);
        return ResponseEntity.ok().body(recipeLikesResponse);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long recipeId
    ) {
        recipeLikeService.unlikeRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

}
