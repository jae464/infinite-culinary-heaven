package com.culinaryheaven.domain.contest.service;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.domain.contest.dto.request.ContestCreateRequest;
import com.culinaryheaven.domain.contest.repository.ContestRepository;
import com.culinaryheaven.domain.contest.repository.TopicIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContestService {

    private final ContestRepository contestRepository;
    private final TopicIngredientRepository topicIngredientRepository;

    public Long create(final ContestCreateRequest request) {
        TopicIngredient topicIngredient = topicIngredientRepository.findById(request.topicIngredientId()).orElseThrow(IllegalArgumentException::new);

        Contest contest = Contest.builder()
                .description(request.description())
                .topicIngredient(topicIngredient)
                .build();

        contestRepository.save(contest);
        return contest.getId();
    }

}
