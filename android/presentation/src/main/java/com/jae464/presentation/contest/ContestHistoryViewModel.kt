package com.jae464.presentation.contest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.ContestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContestHistoryViewModel @Inject constructor(
    private val contestRepository: ContestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContestHistoryUiState())
    val uiState: StateFlow<ContestHistoryUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var isLastPage = false

    init {
        fetchContests()
    }

    fun handleIntent(intent: ContestHistoryIntent) {
        when (intent) {
            ContestHistoryIntent.LoadContestHistory -> fetchContests()
        }
    }

    private fun fetchContests() {
        if (isLastPage || uiState.value.isLoading) return

        _uiState.update { state -> state.copy(isLoading = true) }

        viewModelScope.launch {
            contestRepository.getAllContests(currentPage)
                .onSuccess {
                    if (it.isEmpty()) {
                        isLastPage = true
                    } else {
                        currentPage++
                    }
                    _uiState.update { state -> state.copy(contests = state.contests + it, isLoading = false) }
                }
                .onFailure {
                    Log.e("ContestHistoryViewModel", "fetchContests Failed")
                    _uiState.update { state -> state.copy(isLoading = false) }
                }
        }
    }



}