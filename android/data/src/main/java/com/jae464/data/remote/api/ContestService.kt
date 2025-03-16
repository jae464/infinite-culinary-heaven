package com.jae464.data.remote.api

import com.jae464.data.remote.model.response.contest.ContestResponse
import com.jae464.data.remote.model.response.contest.ContestsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContestService {
    @GET("/contests")
    suspend fun getAllContests(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<ContestsResponse>

    @GET("/contests/current")
    suspend fun getCurrentContest(): Response<ContestResponse>

}