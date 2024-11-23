package com.jae464.data.repository

import com.jae464.data.remote.api.ContestService
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.makeErrorResponse
import com.jae464.domain.model.Contest
import com.jae464.domain.repository.ContestRepository
import javax.inject.Inject

class DefaultContestRepository @Inject constructor(
    private val contestService: ContestService
) : ContestRepository {
    override suspend fun getCurrentContest(): Result<Contest> {
        val response = contestService.getCurrentContest()
        return if (response.isSuccessful) {
            val contestResponse = response.body()
            if (contestResponse != null) {
                Result.success(contestResponse.toDomain())
            } else {
                Result.failure(
                    Exception(makeErrorResponse(
                        response.code(),
                        response.message(),
                        response.errorBody().toString())
                    )
                )
            }
        } else {
            Result.failure(Exception("network error"))
        }
    }

    override suspend fun getAllContests(): Result<List<Contest>> {
        val response = contestService.getAllContests()
        return if (response.isSuccessful) {
            val contestsResponse = response.body()
            if (contestsResponse != null) {
                Result.success(contestsResponse.toDomain())
            } else {
                Result.failure(
                    Exception(makeErrorResponse(
                        response.code(),
                        response.message(),
                        response.errorBody().toString())
                    )
                )
            }

        } else {
            Result.failure(Exception("network error"))
        }

    }
}