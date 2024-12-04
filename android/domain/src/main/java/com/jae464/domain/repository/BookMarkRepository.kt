package com.jae464.domain.repository

import com.jae464.domain.model.BookMark
import com.jae464.domain.model.RecipePreview
import kotlinx.coroutines.flow.Flow

interface BookMarkRepository {
    suspend fun getBookMarkedRecipes(): Result<List<BookMark>>
    fun getBookMarkFlow(): Flow<String>
    suspend fun addBookMark(recipeId: Long): Result<Unit>
    suspend fun deleteBookMark(recipeId: Long): Result<Unit>
    suspend fun isBookMarked(recipeId: Long): Result<Boolean>
}