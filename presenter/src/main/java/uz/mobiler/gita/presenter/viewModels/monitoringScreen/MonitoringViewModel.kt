package uz.mobiler.gita.presenter.viewModels.monitoringScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.viewmodel.container
import uz.mobiler.gita.core.toUiModel
import uz.mobiler.gita.presenter.util.NetworkMonitor
import uz.mobiler.gita.usecase.CheckCardUseCase
import uz.mobiler.gita.usecase.GetTransactionHistoryUseCase
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MonitoringViewModel @Inject constructor(
    private val getTransactionHistoryUseCase: GetTransactionHistoryUseCase,
    private val checkCardUseCase: CheckCardUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel(), MonitoringContract.ViewModel {

    override val container =
        container<MonitoringContract.UiState, MonitoringContract.SideEffect>(
            MonitoringContract.UiState()
        )

    private var currentCardId: String = ""
    private var currentType: String = ""

    init {
        onEventDispatcher(
            MonitoringContract.Intent.Refresh(
                cardId = currentCardId,
                type = currentType
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onEventDispatcher(intent: MonitoringContract.Intent) = intent {
        when (intent) {

            is MonitoringContract.Intent.LoadTransactions -> {
                loadData()
            }

            is MonitoringContract.Intent.Refresh -> {
                currentCardId = intent.cardId
                currentType = intent.type
                loadData()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadData() = intent {

        if (!networkMonitor.checkConnection()) {
            postSideEffect(MonitoringContract.SideEffect.NoConnection)
            return@intent
        }

        val transactionsFlow = getTransactionHistoryUseCase(
            cardId = currentCardId,
            type = currentType
        )
            .cachedIn(viewModelScope)
            .map { it.toUiModel() }

        reduce { state.copy(transactions = transactionsFlow) }
        postSideEffect(MonitoringContract.SideEffect.ScrollToTop)
    }
}