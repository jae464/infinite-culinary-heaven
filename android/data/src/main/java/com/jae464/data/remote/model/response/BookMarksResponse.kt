package com.jae464.data.remote.model.response

import com.jae464.domain.model.BookMark
import kotlinx.serialization.Serializable

@Serializable
data class BookMarksResponse(
    val bookMarks: List<BookMarkResponse>
)

fun BookMarksResponse.toDomain(): List<BookMark>  {
    return bookMarks.map { it.toDomain() }
}