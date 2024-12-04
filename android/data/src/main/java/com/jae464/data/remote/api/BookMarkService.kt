package com.jae464.data.remote.api

import com.jae464.data.remote.model.request.BookMarkCreateRequest
import com.jae464.data.remote.model.response.BookMarkResponse
import com.jae464.data.remote.model.response.BookMarksResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookMarkService {

    @POST("/bookmarks")
    suspend fun addBookMark(@Body request: BookMarkCreateRequest): Response<BookMarkResponse>

    @GET("/bookmarks")
    suspend fun getAllBookMarks(): Response<BookMarksResponse>

    @DELETE("/bookmarks/{bookMarkId}")
    suspend fun deleteBookMark(@Path("bookMarkId") bookMarkId: Long): Response<Unit>

}