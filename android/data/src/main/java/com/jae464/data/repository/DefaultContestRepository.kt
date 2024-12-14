package com.jae464.data.repository

import com.jae464.data.remote.api.ContestService
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.handleResponse
import com.jae464.data.util.makeErrorResponse
import com.jae464.domain.model.Contest
import com.jae464.domain.repository.ContestRepository
import javax.inject.Inject

class DefaultContestRepository @Inject constructor(
    private val contestService: ContestService
) : ContestRepository {
    override suspend fun getCurrentContest(): Result<Contest> {
        return handleResponse {
            contestService.getCurrentContest()
        }.mapCatching { response ->
            response.toDomain()
        }
    }

    override suspend fun getAllContests(): Result<List<Contest>> {
        return handleResponse {
            contestService.getAllContests()
        }.mapCatching { response ->
            response.toDomain()
        }

    }
}