package com.jae464.data.remote.api

import com.jae464.data.remote.model.request.DeviceTokenUpdateRequest
import com.jae464.data.remote.model.response.DeviceTokenResponse
import com.jae464.data.remote.model.response.UserInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface UserService {

    @GET("/users/me")
    suspend fun getMyInfo(): Response<UserInfoResponse>

    @Multipart
    @PATCH("/users/me")
    suspend fun updateMyInfo(
        @Part("request") body: RequestBody,
        @Part profileImage: MultipartBody.Part?
    ): Response<UserInfoResponse>

    @PATCH("/device-toke")
    suspend fun updateDeviceToken(
        @Body request: DeviceTokenUpdateRequest
    ): Response<DeviceTokenResponse>

}