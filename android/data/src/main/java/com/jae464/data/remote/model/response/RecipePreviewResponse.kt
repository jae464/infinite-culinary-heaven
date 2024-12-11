package com.jae464.data.remote.model.response

import com.jae464.data.util.adjustLocalhostUrl
import com.jae464.domain.model.RecipePreview
import kotlinx.serialization.Serializable

@Serializable
data class RecipePreviewResponse(
    val id: Long,
    val title: String,
    val description: String,
    val thumbnailImageUrl: String,
    val writerInfoResponse: WriterInfoResponse,
    val contest: ContestResponse,
    val bookMarkCounts: Int,
    val likeCounts: Int,
    val commentCounts: Int,
)

fun RecipePreviewResponse.toDomain() = RecipePreview(
    id = id,
    title = title,
    imageUrl = adjustLocalhostUrl(thumbnailImageUrl),
    description = description,
    score = 5f,
    bookMarkCounts = bookMarkCounts,
    likeCounts = likeCounts,
    commentCounts = commentCounts,
    author = writerInfoResponse.nickname,
)


