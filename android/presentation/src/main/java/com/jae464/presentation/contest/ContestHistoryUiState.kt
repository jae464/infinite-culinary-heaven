package com.jae464.presentation.contest

import com.jae464.domain.model.Contest

data class ContestHistoryUiState(
    val isLoading: Boolean = false,
    val contests: List<Contest> = emptyList(),
)
