package uz.mobiler.gita.presenter.viewModels.payingScreen

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.mobiler.gita.core.models.CardData

interface PayingContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val cards: List<CardData> = emptyList(),
        val fromCard: CardData? = CardData("","","","",0L,"",false,false,"UZCARD"),
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
        data class OnPay(val cardId: String,val providerId:String,val amount:Long, val account:String) : Intent
        data class OnChangeCurrent(val cardData: CardData) : Intent
        data object OnLoadData : Intent
    }
}