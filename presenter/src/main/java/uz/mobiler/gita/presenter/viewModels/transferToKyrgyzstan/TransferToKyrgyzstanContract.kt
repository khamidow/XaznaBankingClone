package uz.mobiler.gita.presenter.viewModels.transferToKyrgyzstan

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.mobiler.gita.core.models.CardData

interface TransferToKyrgyzstanContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val cards: List<CardData> = emptyList(),
        val fromCard: CardData? = CardData("","","","",0L,"",false,false,"UZCARD"),
        val foundName: String = "",
        val nameDialogState: Boolean = false,
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
        data class OnCheckCard(val cardNumber: String) : Intent
        data class OnTransfer(val cardId: String,val toCardNumber:String,val amount:Long) : Intent
        data class OnChangeCurrent(val cardData: CardData) : Intent
        data object OnLoadData : Intent
        data object OnCloseNameSheet : Intent
        data object OnOpenNameSheet : Intent
    }
}