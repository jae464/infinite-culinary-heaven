package com.culinaryheaven.domain.recipe.repository;

import com.culinaryheaven.domain.recipe.domain.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
}
