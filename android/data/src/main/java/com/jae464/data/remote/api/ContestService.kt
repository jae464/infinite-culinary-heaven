package com.jae464.data.remote.api

import com.jae464.data.remote.model.response.ContestResponse
import com.jae464.data.remote.model.response.ContestsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ContestService {
    @GET("/contests")
    suspend fun getAllContests(): Response<ContestsResponse>

    @GET("/contests/current")
    suspend fun getCurrentContest(): Response<ContestResponse>

}