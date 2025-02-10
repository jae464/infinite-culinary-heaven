package com.culinaryheaven.domain.recipe.controller;

import com.culinaryheaven.domain.recipe.service.RecipeReportService;
import com.culinaryheaven.global.annotation.Authenticated;
import com.culinaryheaven.global.security.PrincipalUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeReportController {

    private final RecipeReportService recipeReportService;

    @PostMapping("/report/{recipeId}")
    public ResponseEntity<Void> report(
            @Authenticated PrincipalUserInfo userInfo,
            @PathVariable Long recipeId
    ) {
        recipeReportService.report(recipeId, userInfo.oauth2Id());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
