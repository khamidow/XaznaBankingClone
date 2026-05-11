package uz.mobiler.gita.presenter.viewModels.homeScreen

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
        val mainCard: CardData? = null,
        val loading: Boolean = false,
        val message: String = ""
    )

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
        data object NoConnection : SideEffect
    }

    sealed interface Intent {
        data object OnLoadData : Intent
    }
}