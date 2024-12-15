package com.jae464.culinaryheaven

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("CustomFirebaseMessagingService", token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("CustomFirebaseMessagingService", remoteMessage.toString())
    }

    private fun sendNotification(title: String, body: String) {

    }

}