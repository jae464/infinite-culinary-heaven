package com.culinaryheaven.domain.contest.service;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.domain.contest.dto.request.TopicIngredientCreateRequest;
import com.culinaryheaven.domain.contest.dto.response.TopicIngredientResponse;
import com.culinaryheaven.domain.contest.dto.response.TopicIngredientsResponse;
import com.culinaryheaven.domain.contest.repository.TopicIngredientRepository;
import com.culinaryheaven.domain.image.domain.ImageStorageClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TopicIngredientService {

    private final TopicIngredientRepository topicIngredientRepository;
    private final ImageStorageClient imageStorageClient;

    @Transactional
    public TopicIngredientResponse create(TopicIngredientCreateRequest request, MultipartFile image) {

        String imageUrl = imageStorageClient.uploadImage(image);

        TopicIngredient topicIngredient = request.toEntity();

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
