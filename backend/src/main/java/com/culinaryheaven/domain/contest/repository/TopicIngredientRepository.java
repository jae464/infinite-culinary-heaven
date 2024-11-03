package com.culinaryheaven.domain.contest.repository;

import com.culinaryheaven.domain.contest.domain.TopicIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicIngredientRepository extends JpaRepository<TopicIngredient, Long> {
    Page<TopicIngredient> findAll(Pageable pageable);
}
