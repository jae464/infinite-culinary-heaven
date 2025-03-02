package com.culinaryheaven.domain.recipe.controller;

import com.culinaryheaven.domain.recipe.dto.response.RecipeLikeResponse;
import com.culinaryheaven.domain.recipe.dto.response.RecipeLikesResponse;
import com.culinaryheaven.domain.recipe.service.RecipeLikeService;
import com.culinaryheaven.global.annotation.Authenticated;
import com.culinaryheaven.global.security.PrincipalUserInfo;
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
            @Authenticated PrincipalUserInfo userInfo,
            @PathVariable Long recipeId
    ) {
        RecipeLikeResponse recipeLikeResponse = recipeLikeService.likeRecipe(recipeId, userInfo.userId());
        return ResponseEntity.ok().body(recipeLikeResponse);
    }

    @GetMapping("/mine")
    public ResponseEntity<RecipeLikesResponse> getMyLikes(
            @Authenticated PrincipalUserInfo userInfo,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        RecipeLikesResponse recipeLikesResponse = recipeLikeService.getMyRecipeLikes(pageable, userInfo.userId());
        return ResponseEntity.ok().body(recipeLikesResponse);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> delete(
            @Authenticated PrincipalUserInfo userInfo,
            @PathVariable Long recipeId
    ) {
        recipeLikeService.unlikeRecipe(recipeId, userInfo.userId());
        return ResponseEntity.noContent().build();
    }

}
