package com.culinaryheaven.domain.contest.dto.response;

import com.culinaryheaven.domain.contest.domain.Contest;
import org.springframework.data.domain.Slice;

import java.util.List;

public record ContestsResponse(
    List<ContestResponse> contests
) {
    public static ContestsResponse of(Slice<Contest> contests) {
        List<ContestResponse> contestResponses = contests.getContent()
                .stream()
                .map(ContestResponse::of)
                .toList();

        return new ContestsResponse(contestResponses);
    }
}
