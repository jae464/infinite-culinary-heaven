package com.jae464.presentation.contest

sealed interface ContestHistoryIntent {
    data object LoadContestHistory : ContestHistoryIntent
}
