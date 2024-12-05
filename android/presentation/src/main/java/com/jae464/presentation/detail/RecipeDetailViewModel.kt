package com.jae464.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.BookMarkRepository
import com.jae464.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val bookMarkRepository: BookMarkRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<RecipeDetailEvent>()
    val event: SharedFlow<RecipeDetailEvent> = _event.asSharedFlow()

    fun handleIntent(intent: RecipeDetailIntent) {
        when (intent) {
            is RecipeDetailIntent.FetchRecipe -> fetchRecipe(intent.recipeId)
            is RecipeDetailIntent.DeleteRecipe -> deleteRecipe(intent.recipeId)
            is RecipeDetailIntent.AddBookMark -> addBookMark(intent.recipeId)
            is RecipeDetailIntent.DeleteBookMark -> deleteBookMark(intent.recipeId)
        }
    }

    private fun fetchRecipe(recipeId: Long) {
        viewModelScope.launch {
            recipeRepository.getRecipeById(recipeId)
                .onSuccess { recipe ->
                    _uiState.update { state -> state.copy(recipe = recipe, isBookMarked = bookMarkRepository.isBookMarked(recipe.id)) }
                }
        }
    }

    private fun deleteRecipe(recipeId: Long) {
        viewModelScope.launch {
            recipeRepository.deleteRecipeById(recipeId)
                .onSuccess {
                    _event.emit(RecipeDetailEvent.DeleteSuccess)
                }
        }
    }

    private fun addBookMark(recipeId: Long) {
        viewModelScope.launch {
            bookMarkRepository.addBookMark(recipeId)
                .onSuccess {
                    _uiState.update { state -> state.copy(isBookMarked = true) }
                    _event.emit(RecipeDetailEvent.AddBookMarkSuccess)
                }
                .onFailure {

                }
        }
    }

    private fun deleteBookMark(recipeId: Long) {
        viewModelScope.launch {
            bookMarkRepository.deleteBookMark(recipeId)
                .onSuccess {
                    _uiState.update { state -> state.copy(isBookMarked = false) }
                    _event.emit(RecipeDetailEvent.DeleteBookMarkSuccess)
                }
                .onFailure {

                }
        }

    }

}