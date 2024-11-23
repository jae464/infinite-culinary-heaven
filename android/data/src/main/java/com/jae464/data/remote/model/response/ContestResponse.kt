package com.jae464.data.remote.model.response

import com.jae464.data.util.adjustLocalhostUrl
import com.jae464.domain.model.Contest
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ContestResponse(
    val id: Long,
    val description: String,
    val startDate: String,
    val endDate: String,
    val topicIngredient: TopicIngredientResponse
)

@Serializable
data class TopicIngredientResponse(
    val id: Long,
    val name: String,
    val image: String,
)

fun ContestResponse.toDomain() = Contest(
    id = id,
    description = description,
    startDate = LocalDateTime.parse(startDate),
    ingredient = topicIngredient.name,
    imageUrl = adjustLocalhostUrl(topicIngredient.image)
)


