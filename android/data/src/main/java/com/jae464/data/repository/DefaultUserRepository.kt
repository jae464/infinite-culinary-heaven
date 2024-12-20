package com.jae464.data.repository

import android.util.Log
import com.jae464.data.remote.api.UserService
import com.jae464.data.remote.model.request.DeviceTokenUpdateRequest
import com.jae464.data.remote.model.request.UserUpdateRequest
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.handleResponse
import com.jae464.domain.model.DeviceToken
import com.jae464.domain.model.UserInfo
import com.jae464.domain.repository.UserRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val userService: UserService
): UserRepository {

    override suspend fun getMyInfo(): Result<UserInfo> {
        return handleResponse {
            userService.getMyInfo()
        }.mapCatching { response ->
            response.toDomain()
        }
    }

    override suspend fun updateProfile(
        nickname: String,
        image: File?,
        ): Result<UserInfo> {
        val body = Json.encodeToString(UserUpdateRequest.serializer(), UserUpdateRequest(nickname))
            .toRequestBody("application/json".toMediaType())
        val fileBody = image?.asRequestBody("image/*".toMediaTypeOrNull())
        var multiFile: MultipartBody.Part? = null
        if (fileBody != null) {
            multiFile = MultipartBody.Part.createFormData("profileImage", image.name, fileBody)
        }

        Log.d("DefaultUserRepository", "$multiFile")

        return handleResponse {
            userService.updateMyInfo(body = body, profileImage = multiFile)
        }.mapCatching { response ->
            response.toDomain()
        }
    }

    override suspend fun updateDeviceToken(token: String): Result<DeviceToken> {
        return handleResponse {
            userService.updateDeviceToken(DeviceTokenUpdateRequest(token))
        }.mapCatching { response ->
            response.toDomain()
        }
    }

}