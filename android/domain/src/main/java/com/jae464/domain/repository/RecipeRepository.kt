package com.jae464.domain.repository

import com.jae464.domain.model.RecipePreview

interface RecipeRepository {
    suspend fun getRecipePreviews(): List<RecipePreview>
}