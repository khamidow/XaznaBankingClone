package uz.mobiler.gita.entity.notificationHelper

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import uz.mobiler.gita.core.R
import kotlin.random.Random


object NotificationHelper {

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        NotificationChannel(
            "notif_channel_id",
            "channel_name",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "channel_description"
            manager.createNotificationChannel(this)
        }

    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun createNotification(context: Context, body: String) {
        val notification = NotificationCompat.Builder(context, "notif_channel_id")
            .setSmallIcon(R.drawable.ic_xazna_logo_light)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle("New API Call")
            .setContentText(body)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(body)
                    .setBigContentTitle("New API Call")
            )
            .setAutoCancel(false)
            .build()

        NotificationManagerCompat.from(context).notify(
            Random.nextInt(9999),
            notification,
        )

    }

}