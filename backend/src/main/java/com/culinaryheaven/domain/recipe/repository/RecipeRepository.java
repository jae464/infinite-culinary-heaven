package com.culinaryheaven.domain.recipe.repository;

import com.culinaryheaven.domain.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
