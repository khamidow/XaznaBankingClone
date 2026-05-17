package uz.mobiler.gita.presenter.viewModels.profileDetailScreen

import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.mobiler.gita.presenter.viewModels.homeScreen.HomeContract

interface ProfileDetailContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val showKycButton:Boolean=false,
        val loading: Boolean = false,
        val message: String = ""
    )

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
        data object NoConnection : SideEffect
        data object PopBack : SideEffect
    }

    sealed interface Intent {
        data object OnLoad: Intent
        data class OnUpdateName(val name: String) : Intent
    }
}