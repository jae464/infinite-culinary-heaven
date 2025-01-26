package com.jae464.data.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.jae464.data.di.BookMarkDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookMarkLocalDataSource @Inject constructor(
    @BookMarkDataStore private val dataStore: DataStore<Preferences>
) {
    suspend fun setBookMarkedRecipeIds(recipeIds: Set<String>) {
        dataStore.edit { preferences ->
            preferences[BOOKMARK_KEY] = recipeIds
        }
    }

    suspend fun addBookMarkedRecipeId(recipeId: String) {
        dataStore.edit { preferences ->
            val currentSet = preferences[BOOKMARK_KEY] ?: emptySet()
            val updatedSet = currentSet + recipeId
            preferences[BOOKMARK_KEY] = updatedSet
        }
    }

    suspend fun deleteBookMarkedId(recipeId: String) {
        dataStore.edit { preferences ->
            val currentSet = preferences[BOOKMARK_KEY] ?: emptySet()
            val updatedSet = currentSet - recipeId
            preferences[BOOKMARK_KEY] = updatedSet
        }
    }

    fun getBookMarkedRecipeIds(): Flow<Set<String>> {
        return dataStore.data.map { preferences ->
            preferences[BOOKMARK_KEY] ?: emptySet()
        }
    }

    companion object {
        private val BOOKMARK_KEY = stringSetPreferencesKey("bookmark")
    }
}