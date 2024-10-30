package com.jae464.domain.repository

import com.jae464.domain.model.RecipePreview

interface BookMarkRepository {
    suspend fun getBookMarkedRecipes(): Result<List<RecipePreview>>
    suspend fun addBookMark(recipeId: Long): Result<Unit>
    suspend fun deleteBookMark(recipeId: Long): Result<Unit>
    suspend fun isBookMarked(recipeId: Long): Result<Boolean>
}