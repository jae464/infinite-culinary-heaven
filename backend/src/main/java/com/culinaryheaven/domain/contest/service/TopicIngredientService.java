package com.culinaryheaven.domain.contest.service;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.domain.contest.dto.request.TopicIngredientCreateRequest;
import com.culinaryheaven.domain.contest.repository.TopicIngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicIngredientService {

    private final TopicIngredientRepository topicIngredientRepository;

    @Transactional
    public void create(TopicIngredientCreateRequest request) {

        TopicIngredient topicIngredient = TopicIngredient.builder()
                .name(request.name())
                .image(request.image())
                .build();

        topicIngredientRepository.save(topicIngredient);
    }
}
