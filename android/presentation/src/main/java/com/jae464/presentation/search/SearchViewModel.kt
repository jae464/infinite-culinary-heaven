package com.jae464.presentation.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _keyword = MutableStateFlow("")
    val keyword: StateFlow<String> = _keyword.asStateFlow()

    init {
        observeKeywordChanges()
    }

    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.UpdateKeyword -> updateKeyword(intent.keyword)
        }
    }

    private fun updateKeyword(keyword: String) {
        _keyword.value = keyword
    }

    private fun observeKeywordChanges() {
        _keyword
            .debounce(500)
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .onEach { keyword ->
                recipeRepository.searchByKeyword(keyword)
                    .onSuccess {
                        _uiState.value = _uiState.value.copy(recipes = it)
                    }
                    .onFailure {
                        Log.e("SearchViewModel", "Error fetching recipes: $it")
                    }
            }
            .launchIn(viewModelScope) // viewModelScope에서 실행
    }

}