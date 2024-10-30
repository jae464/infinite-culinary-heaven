package com.jae464.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.BookMarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    }

    private fun fetchBookMarkedRecipes() {
        // TODO: Fetch bookmarked recipes from the repository
        viewModelScope.launch {
            bookMarkRepository.getBookMarkedRecipes()
                .onSuccess { recipes ->
                    _uiState.update { state -> state.copy(bookMarkedRecipes = recipes) }
                }
                .onFailure {
                    // Handle error
                }
        }

    }

}