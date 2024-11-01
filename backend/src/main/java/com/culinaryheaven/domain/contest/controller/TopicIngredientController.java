package com.culinaryheaven.domain.contest.controller;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.domain.contest.dto.request.TopicIngredientCreateRequest;
import com.culinaryheaven.domain.contest.service.TopicIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
