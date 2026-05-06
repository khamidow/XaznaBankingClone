package uz.mobiler.gita.entity.source.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private val pref = context.getSharedPreferences("token_manager", Context.MODE_PRIVATE)

    var accessToken
        get() = pref.getString("accessToken", "").toString()
        set(value) {
            pref.edit().putString("accessToken", value).apply()
        }

    var refreshToken
        get() = pref.getString("refreshToken", "").toString()
        set(value) {
            pref.edit().putString("refreshToken", value).apply()
        }
}