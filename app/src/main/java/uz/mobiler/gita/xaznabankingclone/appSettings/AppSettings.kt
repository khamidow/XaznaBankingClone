package uz.mobiler.gita.xaznabankingclone.appSettings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

enum class AppThemeOption { SYSTEM, LIGHT, DARK,CUSTOM }

private val Context.dataStore by preferencesDataStore("settings")

@Singleton
class AppSettings @Inject constructor(@ApplicationContext private val context: Context) {

    private val THEME_KEY = stringPreferencesKey("theme")
    private val LANG_KEY  = stringPreferencesKey("language")

    val themeFlow: Flow<AppThemeOption> = context.dataStore.data.map { prefs ->
        AppThemeOption.valueOf(prefs[THEME_KEY] ?: AppThemeOption.SYSTEM.name)
    }

    val languageFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[LANG_KEY] ?: "uz"
    }

    suspend fun setTheme(theme: AppThemeOption) {
        context.dataStore.edit { it[THEME_KEY] = theme.name }
    }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { it[LANG_KEY] = lang }
    }
}
