package uz.mobiler.gita.presenter.viewModels.kcyCameraScreen

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface KycCameraContract {

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
        data object NavigateToHome : SideEffect
    }

    sealed interface Intent {
        data class OnSubmitKyc(
            val passportSeries: String,
            val passportNumber: String,
            val birthDate: String,
            val selfieBase64: String
        ) : Intent
    }
}