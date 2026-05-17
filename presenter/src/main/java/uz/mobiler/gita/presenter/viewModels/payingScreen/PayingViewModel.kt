package uz.mobiler.gita.presenter.viewModels.payingScreen

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
import uz.mobiler.gita.usecase.MakePaymentUseCase
import javax.inject.Inject

@HiltViewModel
class PayingViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val makePaymentUseCase: MakePaymentUseCase,
    private val networkMonitor: NetworkMonitor
) : PayingContract.ViewModel, ViewModel() {

    override val container =
        container<PayingContract.UiState, PayingContract.SideEffect>(
            PayingContract.UiState()
        )

    init {
        onEventDispatcher(PayingContract.Intent.OnLoadData)
    }

    override fun onEventDispatcher(intent: PayingContract.Intent) =
        intent @RequiresPermission(
            Manifest.permission.ACCESS_NETWORK_STATE
        ) {
            when (intent) {
                is PayingContract.Intent.OnLoadData -> {
                    if (networkMonitor.checkConnection()) {
                        getCardsUseCase().onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                if (it.isNotEmpty()) {
                                    reduce { state.copy(fromCard = it[0]) }
                                    reduce { state.copy(cards = it) }
                                } else {
                                    postSideEffect(PayingContract.SideEffect.ShowMessage("Card not found"))
                                    postSideEffect(PayingContract.SideEffect.PopBack)
                                }
                            }.onFailure {
                                postSideEffect(PayingContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(PayingContract.SideEffect.NoConnection)
                    }
                }

                is PayingContract.Intent.OnPay -> {
                    if (networkMonitor.checkConnection()) {
                        makePaymentUseCase(
                            intent.providerId,
                            intent.cardId,
                            intent.amount,
                            intent.account
                        ).onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                postSideEffect(PayingContract.SideEffect.ShowMessage("Payment successful"))
                                postSideEffect(PayingContract.SideEffect.PopBack)
                            }.onFailure {
                                postSideEffect(PayingContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(PayingContract.SideEffect.NoConnection)
                    }
                }

                is PayingContract.Intent.OnChangeCurrent -> {
                    reduce { state.copy(fromCard = intent.cardData) }
                }
            }
        }
}

