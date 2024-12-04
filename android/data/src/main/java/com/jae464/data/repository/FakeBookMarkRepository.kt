package com.jae464.data.repository

import com.jae464.domain.model.BookMark
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.repository.BookMarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeBookMarkRepository @Inject constructor() : BookMarkRepository {
    override suspend fun getBookMarkedRecipes(): Result<List<BookMark>> {
        return Result.success(
            listOf(

            )
        )
    }

    override fun getBookMarkFlow(): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun addBookMark(recipeId: Long): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBookMark(recipeId: Long): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun isBookMarked(recipeId: Long): Result<Boolean> {
        TODO("Not yet implemented")
    }
}