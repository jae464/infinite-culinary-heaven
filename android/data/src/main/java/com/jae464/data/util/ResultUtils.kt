package com.jae464.data.util

import retrofit2.Response

suspend fun <T> handleResponse(execute: suspend () -> Response<T>): Result<T> {
    return try {
        val response = execute()

        if (response.isSuccessful) {

            if(response.code() == 204) {
                return Result.success(Unit as T)
            }

            val body = response.body()

            if (body != null) {
                Result.success(body)
            }
            else {
                Result.failure(Exception(
                    makeErrorResponse(
                        response.code(),
                        response.errorBody()?.string()
                    )
                ))
            }
        } else {
            Result.failure(Exception(
                makeErrorResponse(
                    response.code(),
                    response.errorBody()?.string()
                )
            ))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}


