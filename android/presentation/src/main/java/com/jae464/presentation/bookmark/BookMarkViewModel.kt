package com.jae464.presentation.bookmark

import android.util.Log
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

    private var currentPage = 0
    private var isLastPage = false

    init {

        fetchBookMarkedRecipes()

    }

    fun handleIntent(intent: BookMarkIntent) {
        when (intent) {
            BookMarkIntent.FetchBookMarkedRecipes -> fetchBookMarkedRecipes()
            BookMarkIntent.InitBookMarkedRecipes -> {
                currentPage = 0
                isLastPage = false
                _uiState.update { state -> state.copy(bookMarkedRecipes = emptyList()) }
                fetchBookMarkedRecipes()
            }
        }
    }

    private fun fetchBookMarkedRecipes() {
        if (isLastPage) return
        if (uiState.value.isLoading) return
        _uiState.update { state -> state.copy(isLoading = true) }
        viewModelScope.launch {
            bookMarkRepository.getBookMarkedRecipes(currentPage)
                .onSuccess { bookmarks ->
                    if (bookmarks.isEmpty()) {
                        isLastPage = true
                    }
                    else {
                        currentPage++
                    }
                    _uiState.update { state -> state.copy(bookMarkedRecipes = state.bookMarkedRecipes + bookmarks.map { it.recipe }, isLoading = false) }
                }
                .onFailure {
                    // Handle error
                    Log.e("BookMarkViewModel", "fetchBookMarkedRecipes failed")
                }
        }

    }

}