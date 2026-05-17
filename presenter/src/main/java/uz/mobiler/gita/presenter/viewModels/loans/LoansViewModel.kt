package uz.mobiler.gita.presenter.viewModels.loans

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import uz.mobiler.gita.presenter.util.NetworkMonitor
import uz.mobiler.gita.usecase.GetAllLoansUseCase
import javax.inject.Inject

@HiltViewModel
class LoansViewModel @Inject constructor(
    private val getAllLoansUseCase: GetAllLoansUseCase,
    private val networkMonitor: NetworkMonitor
) : LoansContract.ViewModel, ViewModel() {

    override val container =
        container<LoansContract.UiState, LoansContract.SideEffect>(LoansContract.UiState())

    init {
        onEventDispatcher(LoansContract.Intent.OnLoadData)
    }

    override fun onEventDispatcher(intent: LoansContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is LoansContract.Intent.OnLoadData -> {
                if (networkMonitor.checkConnection()) {
                    getAllLoansUseCase().onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion {
                        reduce { state.copy(loading = false) }
                    }.onEach { result ->
                        result.onSuccess { loans ->
                            val activeLoans = loans.filter { it.status == "ACTIVE" }
                            val inactiveLoans = loans.filter { it.status != "ACTIVE" }
                            reduce {
                                state.copy(
                                    activeLoans = activeLoans,
                                    inactiveLoans = inactiveLoans
                                )
                            }
                        }.onFailure {
                            postSideEffect(LoansContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    postSideEffect(LoansContract.SideEffect.NoConnection)
                }
            }

            is LoansContract.Intent.OnTabSelected -> {
                reduce { state.copy(isActiveTab = intent.isActive) }
            }
        }
    }
}