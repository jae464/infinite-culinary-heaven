package com.jae464.presentation.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jae464.domain.repository.UserRepository
import com.jae464.presentation.util.notification.CulinaryNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class CustomFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var culinaryNotificationManager: CulinaryNotificationManager

    override fun onNewToken(token: String) {
        Log.d("CustomFirebaseMessagingService", token)
        runBlocking {
            withContext(Dispatchers.IO) {
                userRepository.updateDeviceToken(token)
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("CustomFirebaseMessagingService", remoteMessage.toString())
        val title = remoteMessage.notification?.title ?: return
        val body = remoteMessage.notification?.body ?: return
        val recipeId = remoteMessage.data["recipeId"] ?: return
        sendNotification(title, body, recipeId.toLong())
    }

    private fun sendNotification(title: String, body: String, recipeId: Long) {
        culinaryNotificationManager.postRecipeLikeNotification(
            title = title, body =  body, imageUrl = null, recipeId = recipeId
        )
    }

}