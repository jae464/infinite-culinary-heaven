package com.culinaryheaven.domain.contest.repository;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicIngredientRepository extends JpaRepository<TopicIngredient, Long> {
}
