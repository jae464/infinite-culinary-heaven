package com.jae464.data.repository

import com.jae464.data.remote.api.ContestService
import com.jae464.data.remote.model.response.toDomain
import com.jae464.domain.model.Contest
import com.jae464.domain.repository.ContestRepository
import java.time.LocalDateTime
import javax.inject.Inject

class DefaultContestRepository @Inject constructor(
    private val contestService: ContestService
) : ContestRepository {
    override suspend fun getCurrentContest(): Result<Contest> {
        return Result.success(
            Contest(
                id = 1L,
                ingredient = "감자",
                imageUrl = "https://nutrionemall.edge.naverncp.com/upload/mgz/46/20210723_57173.png",
                description = "이번 주 요리의 주 재료는 감자입니다! 다양한 감자 요리를 시도해보세요.",
                startDate = LocalDateTime.now()
            )
        )
    }

    override suspend fun getAllContests(): Result<List<Contest>> {
        val response = contestService.getAllContests()
        return if (response.isSuccessful) {
            val contestsResponse = response.body()
            if (contestsResponse != null) {
                Result.success(contestsResponse.toDomain())
            } else {
                Result.failure(Exception("response is null"))
            }

        } else {
            Result.failure(Exception("response is not successful"))
        }

    }
}