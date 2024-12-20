package com.jae464.presentation.util.notification

import android.Manifest.permission
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.jae464.presentation.MainActivity
import com.jae464.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val RECIPE_LIKE_CHANNEL_ID = "RECIPE_LIKE_CHANNEL_ID"
private const val RECIPE_LIKE_CHANNEL_NAME = "RECIPE_LIKE_CHANNEL_NAME"
private const val RECIPE_LIKE_NOTIFICATION_GROUP = "RECIPE_LIKE_NOTIFICATIONS"
private const val RECIPE_LIKE_NOTIFICATION_REQUEST_CODE = 0
private const val TARGET_ACTIVITY_NAME = "com.jae464.presentation.MainActivity"

@Singleton
class CulinaryNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
): Notifier {

    override fun postRecipeLikeNotification(title: String, body: String, imageUrl: String?, recipeId: Long) {
        if (checkSelfPermission(context, permission.POST_NOTIFICATIONS) != PERMISSION_GRANTED) {
            return
        }
        val recipeLikeNotification = context.createRecipeLikeNotification {
            setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(context.createRecipeLikePendingIntent(recipeId))
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
//                .setGroup(RECIPE_LIKE_NOTIFICATION_GROUP)
                .setAutoCancel(true)
        }
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(recipeId.toInt(), recipeLikeNotification)
    }

}

private fun Context.createRecipeLikeNotification(
    block: NotificationCompat.Builder.() -> Unit
): Notification {
    createRecipeLikeNotificationChannel()
    return NotificationCompat.Builder(this, RECIPE_LIKE_CHANNEL_ID)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .apply(block)
        .build()
}

private fun Context.createRecipeLikeNotificationChannel() {
    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
        return
    }

    val channel = NotificationChannel(
        RECIPE_LIKE_CHANNEL_ID,
        RECIPE_LIKE_CHANNEL_NAME,
        NotificationManager.IMPORTANCE_DEFAULT
    )

    NotificationManagerCompat.from(this).createNotificationChannel(channel)
}

private fun Context.createRecipeLikePendingIntent(
    recipeId: Long
): PendingIntent? {
    val intent = Intent(this, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra("recipeId", recipeId)
    }

    return TaskStackBuilder.create(this).run {
        addNextIntentWithParentStack(intent)
        getPendingIntent(RECIPE_LIKE_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
}