package uz.mobiler.gita.presenter.viewModels.betweenMyCardsScreen

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.mobiler.gita.core.models.CardData

interface BetweenMyCardsContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val cards: List<CardData> = emptyList(),
        val fromCard: CardData? = CardData("","","","",0L,"",false,false,"UZCARD"),
        val toCard: CardData? = CardData("","","","",0L,"",false,false,"UZCARD"),
        val loading: Boolean = false,
        val message: String = ""
    )

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
        data object NoConnection : SideEffect
        data object PopBack : SideEffect
        data object PinScreen : SideEffect
    }

    sealed interface Intent {
        data class OnTransfer(val cardId: String,val toCardNumber:String,val amount:Long) : Intent
        data class OnChangeCurrent1(val cardData: CardData) : Intent
        data class OnChangeCurrent2(val cardData: CardData) : Intent
        data object OnLoadData : Intent
        data object OnReplacePlaces : Intent
    }
}