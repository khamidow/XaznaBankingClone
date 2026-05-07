package uz.mobiler.gita.presenter.viewModels.verifyScreen

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface VerifyContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val success: Boolean = false,
        val loading: Boolean = false,
        val isNewUser: Boolean = false,
        val message: String = ""
    )

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
        data object NoConnection: SideEffect
    }

    sealed interface Intent {
        data class OnVerifyOtp(val phone: String, val code: String) : Intent
        data class OnSendOtp(val phone: String) : Intent
    }
}