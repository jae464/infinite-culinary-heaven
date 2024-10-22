package com.jae464.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.ContestRepository
import com.jae464.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            val recipePreviews = recipeRepository.getRecipePreviews()
            val currentContest = contestRepository.getCurrentContest()
            _uiState.value = _uiState.value.copy(recipePreviews = recipePreviews, currentContest = currentContest, isLoading = false)
        }
    }

}