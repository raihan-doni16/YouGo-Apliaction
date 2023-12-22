package com.example.yougoapp.ui.schedule

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.yougoapp.R

class DailyReminderReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == "your_package_name.ACTION_DAILY_REMINDER") {

            showNotification(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(context: Context?) {

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "pose",
                "yoga",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }


        val notification = NotificationCompat.Builder(context, "pose")
            .setContentTitle("Daily Reminder")
            .setContentText("Let's Yoga")
            .setSmallIcon(R.drawable.ic_notification)
            .build()
        notificationManager.notify(1, notification)
    }
}