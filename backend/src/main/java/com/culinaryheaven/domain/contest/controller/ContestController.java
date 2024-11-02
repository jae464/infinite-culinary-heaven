package com.culinaryheaven.domain.contest.controller;

import com.culinaryheaven.domain.contest.dto.request.ContestCreateRequest;
import com.culinaryheaven.domain.contest.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contests")
@RequiredArgsConstructor

public class ContestController {

    private final ContestService contestService;

    @PostMapping
    public ResponseEntity<Void> createContest(
            @RequestBody final ContestCreateRequest request
    ) {
        System.out.println(request.toString());
        contestService.create(request);
        return ResponseEntity.ok().build();
    }
}
