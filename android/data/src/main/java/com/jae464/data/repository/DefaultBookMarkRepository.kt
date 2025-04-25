package com.jae464.data.repository

import com.jae464.data.local.datasource.BookMarkLocalDataSource
import com.jae464.data.remote.api.BookMarkService
import com.jae464.data.remote.model.response.bookmark.toDomain
import com.jae464.data.util.handleResponse
import com.jae464.domain.model.BookMark
import com.jae464.domain.repository.BookMarkRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultBookMarkRepository @Inject constructor(
    private val bookMarkService: BookMarkService,
    private val bookMarkLocalDataSource: BookMarkLocalDataSource,
): BookMarkRepository {

    override suspend fun getBookMarkedRecipes(page: Int): Result<List<BookMark>> {
        return handleResponse {
            bookMarkService.getAllBookMarks(page = page)
        }.mapCatching { bookMarksResponse ->
            bookMarkLocalDataSource.setBookMarkedRecipeIds(bookMarksResponse.bookMarks.map { it.recipe.id.toString() }.toSet())
            bookMarksResponse.toDomain()
        }
    }

    override suspend fun addBookMark(recipeId: Long): Result<Unit> {
        return handleResponse {
            bookMarkService.addBookMark(recipeId)
        }.mapCatching {
            bookMarkLocalDataSource.addBookMarkedRecipeId(recipeId.toString())
        }
    }

    override suspend fun deleteBookMark(recipeId: Long): Result<Unit> {
        return handleResponse {
            bookMarkService.deleteBookMark(recipeId)
        }.mapCatching {
            bookMarkLocalDataSource.deleteBookMarkedId(recipeId.toString())
        }

    }

    override suspend fun isBookMarked(recipeId: Long): Boolean {

        return bookMarkLocalDataSource.getBookMarkedRecipeIds().map {
            it.contains(recipeId.toString())
        }.first()

    }
}