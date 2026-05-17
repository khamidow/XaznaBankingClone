package uz.mobiler.gita.presenter.viewModels.loans

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.mobiler.gita.core.models.LoanData

interface LoansContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val activeLoans: List<LoanData> = emptyList(),
        val inactiveLoans: List<LoanData> = emptyList(),
        val isActiveTab: Boolean = true,
        val loading: Boolean = false,
        val message: String = ""
    )

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
        data object NoConnection : SideEffect
    }

    sealed interface Intent {
        data object OnLoadData : Intent
        data class OnTabSelected(val isActive: Boolean) : Intent
    }
}