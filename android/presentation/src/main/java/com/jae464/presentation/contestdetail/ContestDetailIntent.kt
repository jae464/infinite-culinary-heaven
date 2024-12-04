package com.jae464.presentation.contestdetail

sealed interface ContestDetailIntent {
    data class LoadContestDetail(val contestId: Long) : ContestDetailIntent
}