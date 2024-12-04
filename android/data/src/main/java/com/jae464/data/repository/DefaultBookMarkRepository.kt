package com.jae464.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jae464.data.remote.api.BookMarkService
import com.jae464.data.remote.model.request.BookMarkCreateRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.makeErrorResponse
import com.jae464.domain.model.BookMark
import com.jae464.domain.repository.BookMarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultBookMarkRepository @Inject constructor(
    private val bookMarkService: BookMarkService,
    private val dataStore: DataStore<Preferences>
): BookMarkRepository {

    private val BOOKMARK_KEY = stringPreferencesKey("bookmark")

    override suspend fun getBookMarkedRecipes(): Result<List<BookMark>> {

        val response = bookMarkService.getAllBookMarks()

        Log.d("DefaultRecipeRepository", response.toString())

        return if (response.isSuccessful) {
            val bookMarksResponse = response.body()
            if (bookMarksResponse != null) {
                Result.success(bookMarksResponse.toDomain())
            } else {
                Result.failure(
                    Exception(
                        makeErrorResponse(
                            response.code(),
                            response.message(),
                            response.errorBody().toString()
                        )
                    )
                )
            }
        } else {
            Log.d("DefaultRecipeRepository", response.toString())
            Result.failure(Exception("network error"))
        }
    }

    override fun getBookMarkFlow(): Flow<String> {
        return dataStore.data.map {
            it[BOOKMARK_KEY] ?: ""
        }
    }


    override suspend fun addBookMark(recipeId: Long): Result<Unit> {

        val response = bookMarkService.addBookMark(BookMarkCreateRequest(recipeId))
        return if (response.isSuccessful) {

            dataStore.edit { preferences ->
                preferences[BOOKMARK_KEY] = response.body()!!.id.toString()
            }

            Result.success(Unit)
        } else {
            Result.failure(Exception(makeErrorResponse(response.code(), response.message(), response.errorBody().toString())))
        }
    }

    override suspend fun deleteBookMark(recipeId: Long): Result<Unit> {

        val response = bookMarkService.deleteBookMark(recipeId)
        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception(makeErrorResponse(response.code(), response.message(), response.errorBody().toString())))
        }

    }

    override suspend fun isBookMarked(recipeId: Long): Result<Boolean> {
        TODO("Not yet implemented")
    }
}