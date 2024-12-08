package com.jae464.presentation.contestdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
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
import javax.inject.Inject

@HiltViewModel
class ContestDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(ContestDetailUiState())
    val uiState: StateFlow<ContestDetailUiState> = _uiState.asStateFlow()

    private var contestId: Long = -1L
    private var currentage = 0
    private var isLastPage = false

    init {
        contestId = savedStateHandle.get<Long>("contestId") ?: -1L
        fetchRecipePreviews(contestId)
    }

    fun handleIntent(intent: ContestDetailIntent) {
        when (intent) {
            is ContestDetailIntent.LoadContestDetail -> fetchRecipePreviews(contestId)
        }
    }

    private fun fetchRecipePreviews(contestId: Long) {
        if (isLastPage) return

        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }
            runCatching {
                val recipePreviews = recipeRepository.getRecipePreviewsByContestId(currentage, contestId).getOrThrow()
                if (recipePreviews.isEmpty()) {
                    isLastPage = true
                } else {
                    currentage++
                }
                _uiState.update { state -> state.copy(recipePreviews = state.recipePreviews + recipePreviews, isLoading = false) }
            }.onFailure {
                Log.e("HomeViewModel", "fetchRecipePreviews Failed ${it.message}")
                _uiState.update { state -> state.copy(isLoading = false) }
            }
        }
    }

}