package com.jae464.presentation.mypage.myrecipe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyRecipeUiState())
    val uiState: StateFlow<MyRecipeUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var isLastPage = false

    init {
        fetchMyRecipes()
    }

    fun handleIntent(intent: MyRecipeIntent) {
        when (intent) {
            is MyRecipeIntent.FetchMyRecipePreviews -> fetchMyRecipes()
        }
    }

    private fun fetchMyRecipes() {

        if (isLastPage) return

        if (uiState.value.isLoading) return

        _uiState.update { state -> state.copy(isLoading = true) }

        viewModelScope.launch {
            recipeRepository.getMyRecipePreviews(currentPage)
                .onSuccess {
                    if (it.isEmpty()) {
                        isLastPage = true
                    } else {
                        currentPage++
                    }
                    // todo delete log
                    Log.d("MyRecipeViewModel", "fetchMyRecipes: $it")
                    _uiState.update { state -> state.copy(recipes = state.recipes + it, isLoading = false) }
                }
                .onFailure {

                }
        }

    }
}