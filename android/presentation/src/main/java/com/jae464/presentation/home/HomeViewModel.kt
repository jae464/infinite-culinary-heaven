package com.jae464.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.ContestRepository
import com.jae464.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val contestRepository: ContestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var isLastPage = false
    private var isLoading = AtomicBoolean(false)

    init {
        fetchRecipePreviews()
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FetchRecipePreviews -> fetchRecipePreviews()
            is HomeIntent.RefreshRecipePreviews -> refreshRecipePreviews()
        }
    }

    fun refreshRecipePreviews() {
        currentPage = 0
        isLastPage = false
        _uiState.update { state -> state.copy(recipePreviews = emptyList()) }
        fetchRecipePreviews()
    }

    private fun fetchRecipePreviews() {
        if (isLastPage) return
        if (isLoading.get()) return

        viewModelScope.launch {
            runCatching {
                isLoading.getAndSet(true)

                _uiState.update { state -> state.copy(isLoading = true) }
                val currentContest = contestRepository.getCurrentContest().getOrThrow()
                _uiState.update { state -> state.copy(currentContest = currentContest) }

                val recipePreviews = recipeRepository.getRecipePreviewsByContestId(page = currentPage, contestId = currentContest.id).getOrThrow()

                if (recipePreviews.isEmpty()) {
                    isLastPage = true
                } else {
                    currentPage++
                }

                isLoading.getAndSet(false)

                _uiState.update { state -> state.copy(recipePreviews = state.recipePreviews + recipePreviews, isLoading = false) }
                _uiState.update { state -> state.copy(isLoading = false) }
            }.onFailure {
                Log.e("HomeViewModel", "fetchRecipePreviews Failed ${it.message}")
            }
        }
    }

}