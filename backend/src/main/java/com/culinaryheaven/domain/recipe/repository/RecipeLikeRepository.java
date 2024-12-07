package com.culinaryheaven.domain.recipe.repository;

import com.culinaryheaven.domain.bookmark.domain.BookMark;
import com.culinaryheaven.domain.recipe.domain.RecipeLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {
    boolean existsByRecipeIdAndUserId(Long recipeId, Long userId);
    Optional<RecipeLike> findByRecipeIdAndUserId(Long recipeId, Long userId);
}
