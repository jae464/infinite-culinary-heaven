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
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContestServiceTest {

    @Mock
    private ContestRepository contestRepository;

    @Mock
    private TopicIngredientRepository topicIngredientRepository;

    @InjectMocks
    private ContestService contestService;

    private FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .build();
    }

    @Test
    void 주제재료로_새로운_대회를_생성한다() {
        // Given
        Contest contest = fixtureMonkey.giveMeOne(Contest.class);
        TopicIngredient topicIngredient = fixtureMonkey.giveMeOne(TopicIngredient.class);
        Contest savedContest = fixtureMonkey.giveMeBuilder(Contest.class)
                .set("id", contest.getId())
                .set("topicIngredient", topicIngredient)
                .sample();

        ContestCreateRequest request = new ContestCreateRequest(contest.getName(), contest.getDescription(), contest.getStartDate(), contest.getEndDate(), topicIngredient.getId());

        when(topicIngredientRepository.findById(request.topicIngredientId())).thenReturn(Optional.of(topicIngredient));
        when(contestRepository.save(any(Contest.class))).thenReturn(savedContest);

        // When
        ContestResponse response = contestService.create(request);

        // Then
        assertNotNull(response);
        assertEquals(savedContest.getId(), response.id());
        verify(topicIngredientRepository).findById(request.topicIngredientId());
        verify(contestRepository).save(any(Contest.class));
    }

    @Test
    void 주제재료가_없으면_예외를_발생시킨다() {
        // Given
        Contest contest = fixtureMonkey.giveMeOne(Contest.class);
        TopicIngredient topicIngredient = fixtureMonkey.giveMeOne(TopicIngredient.class);
        ContestCreateRequest request = new ContestCreateRequest(contest.getName(), contest.getDescription(), contest.getStartDate(), contest.getEndDate(), topicIngredient.getId());

        when(topicIngredientRepository.findById(request.topicIngredientId())).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> contestService.create(request));
        assertEquals(ErrorCode.TOPIC_INGREDIENT_NOT_FOUND, exception.getErrorCode());
        verify(topicIngredientRepository).findById(request.topicIngredientId());
    }

    @Test
    void 모든_대회목록을_조회한다() {
        // Given
        PageRequest pageable = PageRequest.of(0, 10);
        List<Contest> contests = fixtureMonkey.giveMe(Contest.class, 3);
        when(contestRepository.findAll(pageable)).thenReturn(new PageImpl<>(contests, pageable, 1));

        // When
        ContestsResponse response = contestService.getAllContests(pageable);

        // Then
        assertNotNull(response);
        assertEquals(3, response.contests().size());
        verify(contestRepository).findAll(pageable);
    }

    @Test
    void 현재_진행중인_대회를_조회한다() {
        // Given
        TopicIngredient topicIngredient = fixtureMonkey.giveMeOne(TopicIngredient.class);

        Contest contest = fixtureMonkey.giveMeBuilder(Contest.class)
                .set("id", 1L)
                .set("topicIngredient", topicIngredient)
                .sample();


        when(contestRepository.findContestsWithinCurrentDate(any(LocalDateTime.class)))
                .thenReturn(Optional.of(contest));

        // When
        ContestResponse response = contestService.getCurrentContest();

        // Then
        assertNotNull(response);
        assertEquals(contest.getId(), response.id());
        verify(contestRepository).findContestsWithinCurrentDate(any(LocalDateTime.class));
    }

    @Test
    void 현재_진행중인_대회가_없으면_예외를_발생시킨다() {
        // Given
        when(contestRepository.findContestsWithinCurrentDate(any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> contestService.getCurrentContest());
        assertEquals(ErrorCode.CONTEST_NOT_FOUND, exception.getErrorCode());
        verify(contestRepository).findContestsWithinCurrentDate(any(LocalDateTime.class));
    }
}
