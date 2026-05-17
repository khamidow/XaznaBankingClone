package uz.mobiler.gita.presenter.viewModels.homeScreen

import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.core.models.ExchangeData

interface HomeContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val exchangeRates: List<ExchangeData> = emptyList(),
        val cards: List<CardData> = emptyList(),
        val allSum: Long? = null,
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
        data object OnLoadData : Intent
        data class OnCheckKcy(val screen: Screen):Intent
    }
}