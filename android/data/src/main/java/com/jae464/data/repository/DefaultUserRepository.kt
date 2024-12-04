package com.jae464.data.repository

import com.jae464.data.remote.api.UserService
import com.jae464.data.remote.model.response.toDomain
import com.jae464.data.util.makeErrorResponse
import com.jae464.domain.model.UserInfo
import com.jae464.domain.repository.UserRepository
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val userService: UserService
): UserRepository {

    override suspend fun getMyInfo(): Result<UserInfo> {
        val response = userService.getMyInfo()
        return if (response.isSuccessful) {
            val userInfoResponse = response.body()
            if (userInfoResponse != null) {
                Result.success(
                    userInfoResponse.toDomain()
                )
            } else {
                Result.failure(
                    Exception(
                        makeErrorResponse(
                            response.code(),
                            response.message(),
                            response.errorBody().toString()
                        )
                    )
                )
            }
        } else {
            Result.failure(Exception("network error"))
        }
    }

}