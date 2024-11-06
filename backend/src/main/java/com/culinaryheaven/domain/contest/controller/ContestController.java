package com.culinaryheaven.domain.contest.controller;

import com.culinaryheaven.domain.contest.dto.request.ContestCreateRequest;
import com.culinaryheaven.domain.contest.dto.response.ContestResponse;
import com.culinaryheaven.domain.contest.dto.response.ContestsResponse;
import com.culinaryheaven.domain.contest.service.ContestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contests")
@RequiredArgsConstructor

public class ContestController {

    private final ContestService contestService;

    @PostMapping
    public ResponseEntity<ContestResponse> createContest(
            @RequestBody @Valid final ContestCreateRequest request
    ) {
        final ContestResponse contestResponse = contestService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(contestResponse);
    }

    @GetMapping
    public ResponseEntity<ContestsResponse> getAllContests(
            final Pageable pageable
    ) {
        final ContestsResponse contestsResponse = contestService.getAllContests(pageable);
        return ResponseEntity.ok().body(contestsResponse);
    }

}
