package com.jae464.domain.repository

import com.jae464.domain.model.BookMark
import kotlinx.coroutines.flow.Flow

interface BookMarkRepository {
    suspend fun getBookMarkedRecipes(page: Int): Result<List<BookMark>>
    suspend fun addBookMark(recipeId: Long): Result<Unit>
    suspend fun deleteBookMark(recipeId: Long): Result<Unit>
    suspend fun isBookMarked(recipeId: Long): Boolean
}