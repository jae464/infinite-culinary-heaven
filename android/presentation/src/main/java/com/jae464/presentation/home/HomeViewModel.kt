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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val contestRepository: ContestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchRecipePreviews()
    }

    private fun fetchRecipePreviews() {
        viewModelScope.launch {
            runCatching {
                val currentContest = contestRepository.getCurrentContest().getOrThrow()
                _uiState.update { state -> state.copy(currentContest = currentContest) }

                val recipePreviews = recipeRepository.getRecipePreviews().getOrThrow()
                _uiState.value = _uiState.value.copy(recipePreviews = recipePreviews, isLoading = false)
            }.onFailure {
                Log.e("HomeViewModel", "fetchRecipePreviews Failed")
            }
        }
    }

}