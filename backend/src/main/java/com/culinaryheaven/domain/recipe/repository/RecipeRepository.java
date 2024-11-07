package com.culinaryheaven.domain.recipe.repository;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Page<Recipe> findAllByContestId(Pageable pageable, Long contestId);
}
