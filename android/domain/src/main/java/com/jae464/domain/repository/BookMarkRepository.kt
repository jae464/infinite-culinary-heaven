package com.jae464.domain.repository

import com.jae464.domain.model.BookMark
import kotlinx.coroutines.flow.Flow

interface BookMarkRepository {
    suspend fun getBookMarkedRecipes(): Result<List<BookMark>>
    suspend fun addBookMark(recipeId: Long): Result<Unit>
    suspend fun deleteBookMark(recipeId: Long): Result<Unit>
    fun getBookMarkedRecipeIds(): Flow<Set<String>>
    suspend fun isBookMarked(recipeId: Long): Boolean
}