package com.jae464.presentation.history

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

    init {
        fetchContests()
    }

    private fun fetchContests() {
        viewModelScope.launch {
            contestRepository.getAllContests()
                .onSuccess { contests ->
                    _uiState.update { state -> state.copy(contests = contests) }
                }
        }

    }



}