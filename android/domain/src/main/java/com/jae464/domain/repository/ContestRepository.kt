package com.jae464.domain.repository

import com.jae464.domain.model.Contest

interface ContestRepository {
    suspend fun getCurrentContest(): Result<Contest>
    suspend fun getAllContests(page: Int): Result<List<Contest>>
}