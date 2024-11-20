package com.culinaryheaven.domain.recipe.service;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.contest.repository.ContestRepository;
import com.culinaryheaven.domain.image.domain.ImageStorageClient;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.domain.Step;
import com.culinaryheaven.domain.recipe.dto.request.RecipeCreateRequest;
import com.culinaryheaven.domain.recipe.dto.request.StepCreateRequest;
import com.culinaryheaven.domain.recipe.dto.response.RecipeResponse;
import com.culinaryheaven.domain.recipe.dto.response.RecipesResponse;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import com.culinaryheaven.domain.recipe.repository.StepRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ContestRepository contestRepository;
    private final ImageStorageClient imageStorageClient;
    private final StepRepository stepRepository;

    @Transactional
    public RecipeResponse create(
            RecipeCreateRequest request,
            List<MultipartFile> images
    ) {
        Contest contest = contestRepository.findById(request.contestId()).orElseThrow(() -> new IllegalArgumentException(
                "존재하지 않는 대회입니다."
        ));

        System.out.println("images: " + images.toString());

        Map<String, MultipartFile> imageMap = images.stream()
                .collect(Collectors.toMap(MultipartFile::getOriginalFilename, file -> file));

        for (Map.Entry<String, MultipartFile> entry : imageMap.entrySet()) {
            System.out.println(entry);
            System.out.println(entry.getValue().getOriginalFilename());
        }

        String thumbnailUrl = imageStorageClient.uploadImage(imageMap.get(request.thumbnailImage()));

        Recipe recipe = request.toEntity(thumbnailUrl, contest);

        Recipe savedRecipe = recipeRepository.save(recipe);

        for (StepCreateRequest stepRequest : request.steps()) {
            String stepImageUrl = imageStorageClient.uploadImage(imageMap.get(stepRequest.ImageUrl()));

            Step step = stepRequest.toEntity(stepImageUrl, savedRecipe);
            stepRepository.save(step);
        }

        return RecipeResponse.of(savedRecipe);
    }

    public RecipeResponse findById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
        return RecipeResponse.of(recipe);
    }

    public RecipesResponse findAllByContestId(Pageable pageable, Long id) {
        Slice<Recipe> recipes = recipeRepository.findAllByContestId(pageable, id);
        return RecipesResponse.of(recipes);
    }


}
