package com.jae464.presentation.contestdetail

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
import javax.inject.Inject

@HiltViewModel
class ContestDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ContestDetailUiState())
    val uiState: StateFlow<ContestDetailUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: ContestDetailIntent) {
        when (intent) {
            is ContestDetailIntent.LoadContestDetail -> loadContestDetail(intent.contestId)
        }
    }


    private fun loadContestDetail(contestId: Long) {
        viewModelScope.launch {
            runCatching {
                val recipePreviews = recipeRepository.getRecipePreviewsByContestId(contestId).getOrThrow()
                _uiState.update { state -> state.copy(recipePreviews = recipePreviews) }
            }.onFailure {
                Log.e("HomeViewModel", "fetchRecipePreviews Failed ${it.message}")
                Log.e("HomeViewModel", "fetchRecipePreviews Failed")
            }
        }
    }

}