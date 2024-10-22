package com.jae464.domain.repository

import com.jae464.domain.model.Contest

interface ContestRepository {
    suspend fun getCurrentContest(): Contest
    suspend fun getAllContests(): List<Contest>
}