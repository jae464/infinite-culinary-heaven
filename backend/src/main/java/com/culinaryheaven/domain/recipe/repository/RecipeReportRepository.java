package com.culinaryheaven.domain.recipe.repository;

import com.culinaryheaven.domain.recipe.domain.RecipeReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeReportRepository extends JpaRepository<RecipeReport, Long> {

}
