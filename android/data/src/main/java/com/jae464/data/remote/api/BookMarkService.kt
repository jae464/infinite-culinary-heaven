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
import retrofit2.http.Query

interface BookMarkService {

    @POST("/bookmarks")
    suspend fun addBookMark(@Body request: BookMarkCreateRequest): Response<BookMarkResponse>

    @GET("/bookmarks")
    suspend fun getAllBookMarks(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100, // todo page 로직 구현후 추후 수정
    ): Response<BookMarksResponse>

    @DELETE("/bookmarks/{bookMarkId}")
    suspend fun deleteBookMark(@Path("bookMarkId") bookMarkId: Long): Response<Unit>

}