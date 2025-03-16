package com.jae464.data.remote.api

import com.jae464.data.remote.model.request.auth.LoginRequest
import com.jae464.data.remote.model.request.auth.RefreshTokenRequest
import com.jae464.data.remote.model.response.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/login/oauth2")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("/auth/reissue")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): Response<LoginResponse>

}