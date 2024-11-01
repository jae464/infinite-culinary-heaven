package com.culinaryheaven.domain.recipe.repository;

import com.culinaryheaven.domain.recipe.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
