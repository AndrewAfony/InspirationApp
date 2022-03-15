package com.myapp.inspirationapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.myapp.inspirationapp.R

fun makeNotification(message: String, context: Context) {

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

        val name = Constants.CHANNEL_NAME
        val descriptionText = Constants.CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_bookmark)
        .setContentTitle(Constants.NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, builder.build())
}