package com.jae464.presentation.bookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.BookMarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val bookMarkRepository: BookMarkRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookMarkUiState())
    val uiState: StateFlow<BookMarkUiState> = _uiState.asStateFlow()

    init {

        fetchBookMarkedRecipes()

        viewModelScope.launch {
            bookMarkRepository.getBookMarkFlow().collect {
                fetchBookMarkedRecipes()
            }
        }
    }

    private fun fetchBookMarkedRecipes() {
        viewModelScope.launch {
            bookMarkRepository.getBookMarkedRecipes()
                .onSuccess { recipes ->
                    _uiState.update { state -> state.copy(bookMarkedRecipes = recipes.map { it.recipe }) }
                }
                .onFailure {
                    // Handle error
                }
        }

    }

}