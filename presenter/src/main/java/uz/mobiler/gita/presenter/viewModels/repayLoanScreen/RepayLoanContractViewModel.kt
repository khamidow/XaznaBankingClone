package uz.mobiler.gita.presenter.viewModels.repayLoanScreen

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
import uz.mobiler.gita.usecase.GetCardsUseCase
import uz.mobiler.gita.usecase.RepayLoanUseCase
import javax.inject.Inject

@HiltViewModel
class RepayLoanContractViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val repayLoanUseCase: RepayLoanUseCase,
    private val networkMonitor: NetworkMonitor
) : RepayLoanContract.ViewModel, ViewModel() {

    override val container =
        container<RepayLoanContract.UiState, RepayLoanContract.SideEffect>(
            RepayLoanContract.UiState()
        )

    init {
        onEventDispatcher(RepayLoanContract.Intent.OnLoadData)
    }

    override fun onEventDispatcher(intent: RepayLoanContract.Intent) =
        intent @RequiresPermission(
            Manifest.permission.ACCESS_NETWORK_STATE
        ) {
            when (intent) {
                is RepayLoanContract.Intent.OnLoadData -> {
                    if (networkMonitor.checkConnection()) {
                        getCardsUseCase().onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                if (it.isNotEmpty()) {
                                    reduce { state.copy(fromCard = it[0]) }
                                    reduce { state.copy(cards = it) }
                                } else {
                                    postSideEffect(RepayLoanContract.SideEffect.ShowMessage("Card not found"))
                                    postSideEffect(RepayLoanContract.SideEffect.PopBack)
                                }
                            }.onFailure {
                                postSideEffect(RepayLoanContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(RepayLoanContract.SideEffect.NoConnection)
                    }
                }

                is RepayLoanContract.Intent.OnRepayLoan -> {
                    if (networkMonitor.checkConnection()) {
                        repayLoanUseCase(intent.id, intent.cardId, intent.amount).onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                postSideEffect(RepayLoanContract.SideEffect.ShowMessage("Paid"))
                                postSideEffect(RepayLoanContract.SideEffect.PopBack)
                            }.onFailure {
                                postSideEffect(RepayLoanContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(RepayLoanContract.SideEffect.NoConnection)
                    }
                }

                is RepayLoanContract.Intent.OnChangeCurrent -> {
                    reduce { state.copy(fromCard = intent.cardData) }
                }
            }
        }
}

