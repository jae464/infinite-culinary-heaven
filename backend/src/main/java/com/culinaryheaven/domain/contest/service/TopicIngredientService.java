package com.culinaryheaven.domain.contest.service;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.domain.contest.dto.request.TopicIngredientCreateRequest;
import com.culinaryheaven.domain.contest.dto.response.TopicIngredientResponse;
import com.culinaryheaven.domain.contest.dto.response.TopicIngredientsResponse;
import com.culinaryheaven.domain.contest.repository.TopicIngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicIngredientService {

    private final TopicIngredientRepository topicIngredientRepository;

    @Transactional
    public TopicIngredientResponse create(TopicIngredientCreateRequest request) {

        TopicIngredient topicIngredient = TopicIngredient.builder()
                .name(request.name())
                .image(request.image())
                .build();

        TopicIngredient savedTopicIngredient = topicIngredientRepository.save(topicIngredient);

        return TopicIngredientResponse.of(savedTopicIngredient);
    }

    public TopicIngredientsResponse getAllTopicIngredients(
            Pageable pageable
    ) {
        Slice<TopicIngredient> topicIngredients = topicIngredientRepository.findAll(pageable);
        return TopicIngredientsResponse.of(topicIngredients);
    }
}
