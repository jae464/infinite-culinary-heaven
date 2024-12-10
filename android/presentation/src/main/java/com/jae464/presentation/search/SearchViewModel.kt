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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
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

    private var currentPage = 0
    private var isLastPage = false
    private var isLoading = AtomicBoolean(false)

    init {
        observeKeywordChanges()
    }

    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.UpdateKeyword -> updateKeyword(intent.keyword)
            is SearchIntent.FetchRecipePreviews -> fetchRecipePreviews(keyword.value)
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
                currentPage = 0
                isLastPage = false
                _uiState.update { state -> state.copy(recipePreviews = emptyList()) }
                fetchRecipePreviews(keyword)
            }
            .launchIn(viewModelScope)
    }

    private fun fetchRecipePreviews(keyword: String) {

        if (isLastPage) return
        if (isLoading.get()) return
        if (keyword.isEmpty()) return

        isLoading.getAndSet(true)

        viewModelScope.launch {
            recipeRepository.searchByKeyword(currentPage, keyword)
                .onSuccess {
                    _uiState.update { state -> state.copy(recipePreviews = state.recipePreviews + it, isLoading = false) }
                    currentPage++
                    isLastPage = it.isEmpty()
                    isLoading.getAndSet(false)
                }
                .onFailure {
                    Log.e("SearchViewModel", "Error fetching recipes: $it")
                    isLoading.getAndSet(false)
                }
        }
    }

}