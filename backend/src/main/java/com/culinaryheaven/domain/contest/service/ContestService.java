package com.culinaryheaven.domain.contest.service;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.domain.contest.dto.request.ContestCreateRequest;
import com.culinaryheaven.domain.contest.dto.response.ContestResponse;
import com.culinaryheaven.domain.contest.dto.response.ContestsResponse;
import com.culinaryheaven.domain.contest.repository.ContestRepository;
import com.culinaryheaven.domain.contest.repository.TopicIngredientRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ContestService {

    private final ContestRepository contestRepository;
    private final TopicIngredientRepository topicIngredientRepository;

    public ContestResponse create(final ContestCreateRequest request) {
        TopicIngredient topicIngredient = topicIngredientRepository.findById(request.topicIngredientId())
                .orElseThrow(() -> new CustomException(ErrorCode.TOPIC_INGREDIENT_NOT_FOUND)
        );

        Contest contest = request.toEntity(topicIngredient);

        Contest savedContest = contestRepository.save(contest);
        return ContestResponse.of(savedContest);
    }

    public ContestsResponse getAllContests(Pageable pageable) {
        Page<Contest> contests = contestRepository.findAll(pageable);
        return ContestsResponse.of(contests);
    }

    public ContestResponse getCurrentContest() {
        Contest contest = contestRepository.findContestsWithinCurrentDate(LocalDateTime.now()).orElseThrow(() ->
                new CustomException(ErrorCode.CONTEST_NOT_FOUND)
        );
        return ContestResponse.of(contest);
    }

}
