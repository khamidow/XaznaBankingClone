package uz.mobiler.gita.presenter.viewModels.monitoringScreen

import androidx.paging.PagingData
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import org.orbitmvi.orbit.ContainerHost
import uz.mobiler.gita.core.models.TransactionUiItem

interface MonitoringContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UiState(
        val transactions: Flow<PagingData<TransactionUiItem>>? = null,
        val loading: Boolean = false,
        val message: String = ""
    )

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
        data object NoConnection : SideEffect
        data object ScrollToTop : SideEffect
    }

    sealed interface Intent {
        data object LoadTransactions : Intent
        data class Refresh(val cardId: String, val type: String) : Intent
    }
}