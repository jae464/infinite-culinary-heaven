package com.culinaryheaven.domain.contest.dto.response;

import com.culinaryheaven.domain.contest.domain.Contest;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Slice;

import java.util.List;

public record ContestsResponse(

    @Schema(description = "대회 정보")
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
