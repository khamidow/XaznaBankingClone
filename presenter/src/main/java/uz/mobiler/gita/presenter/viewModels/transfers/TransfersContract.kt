package uz.mobiler.gita.presenter.viewModels.transfers

import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface TransfersContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val loading: Boolean = false,
        val message: String = ""
    )

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
        data object NoConnection : SideEffect
        data object KycDialog: SideEffect
        data class Navigate(val screen: Screen): SideEffect
    }

    sealed interface Intent {
        data class OnCheckKcy(val screen: Screen):Intent
    }
}