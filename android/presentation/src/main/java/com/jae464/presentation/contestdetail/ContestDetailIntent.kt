package com.jae464.presentation.contestdetail

sealed interface ContestDetailIntent {
    data object LoadContestDetail : ContestDetailIntent
}