package uz.mobiler.gita.presenter.viewModels.cardInfoScreen

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.core.models.ExchangeData

interface CardInfoContract {

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
        data object PopBack : SideEffect
    }

    sealed interface Intent {
        data class OnSetMain(val id:String): Intent
        data class OnDeleteCard(val id:String): Intent
    }
}