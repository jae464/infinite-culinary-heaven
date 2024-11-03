package com.culinaryheaven.domain.contest.controller;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.domain.contest.dto.request.TopicIngredientCreateRequest;
import com.culinaryheaven.domain.contest.dto.response.TopicIngredientsResponse;
import com.culinaryheaven.domain.contest.service.TopicIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic-ingredients")
@RequiredArgsConstructor
public class TopicIngredientController {

    private final TopicIngredientService topicIngredientService;

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody final TopicIngredientCreateRequest request
    ) {
        topicIngredientService.create(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<TopicIngredientsResponse> getAllTopicIngredients(
            Pageable pageable
    ) {
        TopicIngredientsResponse topicIngredientsResponse = topicIngredientService.getAllTopicIngredients(pageable);
        return ResponseEntity.ok(topicIngredientsResponse);
    }
}
