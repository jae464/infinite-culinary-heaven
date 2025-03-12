package com.jae464.data.remote.api

import com.jae464.data.remote.model.request.DeviceTokenUpdateRequest
import com.jae464.data.remote.model.response.DeviceTokenResponse
import com.jae464.data.remote.model.response.FollowResponse
import com.jae464.data.remote.model.response.FollowStatusResponse
import com.jae464.data.remote.model.response.UserInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserService {

    @GET("/users/me")
    suspend fun getMyInfo(): Response<UserInfoResponse>

    @GET("/users/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: Long
    ): Response<UserInfoResponse>

    @Multipart
    @PATCH("/users/me")
    suspend fun updateMyInfo(
        @Part("request") body: RequestBody,
        @Part profileImage: MultipartBody.Part?
    ): Response<UserInfoResponse>

    @PATCH("/device-token")
    suspend fun updateDeviceToken(
        @Body request: DeviceTokenUpdateRequest
    ): Response<DeviceTokenResponse>

    @POST("/users/follows/{userId}")
    suspend fun followUser(
        @Path("userId") userId: Long
    ): Response<FollowResponse>

    @GET("/users/follows/status/{userId}")
    suspend fun getFollowStatus(
        @Path("userId") userId: Long
    ): Response<FollowStatusResponse>

    @GET("/users/follows/{userId}")
    suspend fun getFollowers(
        @Path("userId") userId: Long
    ): Response<List<UserInfoResponse>>

    @DELETE("/users/follows/{userId}")
    suspend fun unfollowUser(
        @Path("userId") userId: Long
    ): Response<Unit>

}