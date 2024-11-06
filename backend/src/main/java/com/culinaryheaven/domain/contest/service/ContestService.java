package com.culinaryheaven.domain.contest.service;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import com.culinaryheaven.domain.contest.dto.request.ContestCreateRequest;
import com.culinaryheaven.domain.contest.dto.response.ContestResponse;
import com.culinaryheaven.domain.contest.dto.response.ContestsResponse;
import com.culinaryheaven.domain.contest.exception.TopicIngredientNotFoundException;
import com.culinaryheaven.domain.contest.repository.ContestRepository;
import com.culinaryheaven.domain.contest.repository.TopicIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
                .orElseThrow(() -> new TopicIngredientNotFoundException(
                        "존재하지 않는 재료입니다."
                )
        );

        LocalDateTime endDate = getEndOfWeekSaturday(request.startDate());

        Contest contest = Contest.builder()
                .description(request.description())
                .topicIngredient(topicIngredient)
                .startDate(request.startDate())
                .endDate(endDate)
                .build();

        Contest savedContest = contestRepository.save(contest);
        return ContestResponse.of(savedContest);
    }

    public ContestsResponse getAllContests(Pageable pageable) {
        Slice<Contest> contests = contestRepository.findAll(pageable);
        return ContestsResponse.of(contests);
    }

    private LocalDateTime getEndOfWeekSaturday(LocalDateTime startDate) {
        DayOfWeek dayOfWeek = startDate.getDayOfWeek();

        if (dayOfWeek != DayOfWeek.MONDAY) {
            throw new IllegalArgumentException("시작일은 월요일만 가능합니다.");
        }

        int daysUntilSaturday = DayOfWeek.SATURDAY.getValue() - dayOfWeek.getValue();

        return startDate.plusDays(daysUntilSaturday)
                .with(LocalTime.of(23, 59, 59));
    }

}
