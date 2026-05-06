package uz.mobiler.gita.presenter.viewModels.phoneScreen

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface PhoneContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val loading: Boolean = false,
        val noNetworkConnection: Boolean = false,
        val message: String = ""
    )

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
        data object NavigateVerify : SideEffect
    }

    sealed interface Intent {
        data class OnSendOtp(val phone: String) : Intent
    }
}