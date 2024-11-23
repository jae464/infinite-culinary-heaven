package com.culinaryheaven.domain.recipe.dto.response;

import com.culinaryheaven.domain.recipe.domain.Step;

import java.util.ArrayList;
import java.util.List;

public record StepsResponse(

        List<StepResponse> steps
) {
    public static StepsResponse of(List<Step> steps) {

        List<StepResponse> stepsResponse = steps.stream()
                .map(StepResponse::of)
                .toList();

        return new StepsResponse(stepsResponse);
    }
}
