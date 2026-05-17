package uz.mobiler.gita.presenter.viewModels.paymentsScreen

import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.mobiler.gita.core.models.PaymentData

interface PaymentsContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val paymentProviders: List<PaymentData> = emptyList(),
        val categories: List<String> = emptyList(),
        val currentCategory:String = "",
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
        data class OnSelectCategory(val name: String) : Intent
        data class OnCheckKcy(val screen: Screen): Intent
    }
}