package com.culinaryheaven.domain.recipe.controller;

import com.culinaryheaven.domain.recipe.domain.RecipeReport;
import com.culinaryheaven.domain.recipe.dto.request.RecipeReportCreateRequest;
import com.culinaryheaven.domain.recipe.service.RecipeReportService;
import com.culinaryheaven.global.annotation.Authenticated;
import com.culinaryheaven.global.security.PrincipalUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeReportController {

    private final RecipeReportService recipeReportService;

    @PostMapping("/report")
    public ResponseEntity<Void> report(
            @Authenticated PrincipalUserInfo userInfo,
            @RequestBody RecipeReportCreateRequest request
    ) {
        recipeReportService.report(request, userInfo.oauth2Id());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
