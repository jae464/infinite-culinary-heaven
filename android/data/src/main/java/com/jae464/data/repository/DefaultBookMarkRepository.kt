package com.jae464.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.jae464.data.remote.api.BookMarkService
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.handleResponse
import com.jae464.data.util.makeErrorResponse
import com.jae464.domain.model.BookMark
import com.jae464.domain.repository.BookMarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultBookMarkRepository @Inject constructor(
    private val bookMarkService: BookMarkService,
    private val dataStore: DataStore<Preferences>
): BookMarkRepository {

    private val BOOKMARK_KEY = stringSetPreferencesKey("bookmark")

    override suspend fun getBookMarkedRecipes(): Result<List<BookMark>> {
        return handleResponse {
            bookMarkService.getAllBookMarks()
        }.mapCatching { bookMarksResponse ->
            dataStore.edit { preferences ->
                preferences[BOOKMARK_KEY] = bookMarksResponse.bookMarks.map { it.recipe.id.toString() }.toSet()
            }
            bookMarksResponse.toDomain()
        }
    }


    override suspend fun addBookMark(recipeId: Long): Result<Unit> {

        return handleResponse {
            bookMarkService.addBookMark(recipeId)
        }.runCatching {
            dataStore.edit { preferences ->
                val currentSet = preferences[BOOKMARK_KEY] ?: emptySet()
                val updatedSet = currentSet + recipeId.toString()
                preferences[BOOKMARK_KEY] = updatedSet
            }
        }

    }

    override suspend fun deleteBookMark(recipeId: Long): Result<Unit> {
        return handleResponse {
            bookMarkService.deleteBookMark(recipeId)
        }.runCatching {
            dataStore.edit { preferences ->
                val currentSet = preferences[BOOKMARK_KEY] ?: emptySet()
                val updatedSet = currentSet - recipeId.toString()
                preferences[BOOKMARK_KEY] = updatedSet
            }
        }

    }

    override fun getBookMarkedRecipeIds(): Flow<Set<String>> {

        return dataStore.data.map { preferences ->
            preferences[BOOKMARK_KEY] ?: emptySet()
        }

    }

    override suspend fun isBookMarked(recipeId: Long): Boolean {

        return getBookMarkedRecipeIds().map {
            it.contains(recipeId.toString())
        }.first()

    }
}