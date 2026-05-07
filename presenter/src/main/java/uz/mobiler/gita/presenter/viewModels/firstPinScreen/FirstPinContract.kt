package uz.mobiler.gita.presenter.viewModels.firstPinScreen

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface FirstPinContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val loading: Boolean = false,
        val message: String = ""
    )

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
        data object NavigateLanguage : SideEffect
        data object NoConnection : SideEffect
    }

    sealed interface Intent {
        data object OnLogOut : Intent
    }
}