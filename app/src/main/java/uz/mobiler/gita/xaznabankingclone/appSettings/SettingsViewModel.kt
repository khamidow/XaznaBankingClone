package uz.mobiler.gita.xaznabankingclone.appSettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettings: AppSettings
) : ViewModel() {

    val theme: StateFlow<AppThemeOption> = appSettings.themeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppThemeOption.SYSTEM)

    val language: StateFlow<String> = appSettings.languageFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "uz")

    fun setTheme(theme: AppThemeOption) {
        viewModelScope.launch { appSettings.setTheme(theme) }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch { appSettings.setLanguage(lang) }
    }
}
