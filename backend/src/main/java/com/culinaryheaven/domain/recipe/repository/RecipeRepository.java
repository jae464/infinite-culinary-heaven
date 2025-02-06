package com.culinaryheaven.domain.recipe.repository;

import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query(value = "select distinct r from Recipe r " +
            "join fetch r.user  u " +
            "join fetch r.contest c join fetch c.topicIngredient ct",
            countQuery = "select count(r) from Recipe r")
    Page<Recipe> findAll(Pageable pageable);
    @Query(value = "select distinct r from Recipe r " +
            "join fetch r.user  u " +
            "join fetch r.contest c join fetch c.topicIngredient ct where r.contest.id = :contestId",
            countQuery = "select count(r) from Recipe r where r.contest.id = :contestId")
    Page<Recipe> findAllByContestId(Pageable pageable, Long contestId);
    Page<Recipe> findByTitleContainingIgnoreCase(Pageable pageable, String keyword);
    Page<Recipe> findAllByUserId(Pageable pageable, Long userId);
}
