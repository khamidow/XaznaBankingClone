package uz.mobiler.gita.xaznabankingclone.app

import android.app.Application
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import uz.mobiler.gita.entity.notificationHelper.NotificationHelper

@HiltAndroidApp
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationHelper.createChannel(this)
        }
    }
}