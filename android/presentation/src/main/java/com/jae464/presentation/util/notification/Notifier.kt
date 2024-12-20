package com.jae464.presentation.util.notification

interface Notifier {
    fun postRecipeLikeNotification(title: String, body: String, imageUrl: String?, recipeId: Long)
}