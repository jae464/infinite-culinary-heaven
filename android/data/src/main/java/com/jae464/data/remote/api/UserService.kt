package com.jae464.data.remote.api

import com.jae464.data.remote.model.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("/users/me")
    suspend fun getMyInfo(): Response<UserInfoResponse>

}