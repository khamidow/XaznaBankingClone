package uz.mobiler.gita.presenter.viewModels.transferToCardScreen

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
import uz.mobiler.gita.usecase.CheckCardUseCase
import uz.mobiler.gita.usecase.ConfirmOtpUseCase
import uz.mobiler.gita.usecase.ConfirmPinUseCase
import uz.mobiler.gita.usecase.GetCardsUseCase
import uz.mobiler.gita.usecase.InitiateTransferUseCase
import javax.inject.Inject

@HiltViewModel
class TransferToCardViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val initiateTransferUseCase: InitiateTransferUseCase,
    private val confirmPinUseCase: ConfirmPinUseCase,
    private val confirmOtpUseCase: ConfirmOtpUseCase,
    private val checkCardUseCase: CheckCardUseCase,
    private val networkMonitor: NetworkMonitor
) : TransferToCardContract.ViewModel, ViewModel() {

    override val container =
        container<TransferToCardContract.UiState, TransferToCardContract.SideEffect>(
            TransferToCardContract.UiState()
        )

    init {
        onEventDispatcher(TransferToCardContract.Intent.OnLoadData)
    }

    override fun onEventDispatcher(intent: TransferToCardContract.Intent) =
        intent @RequiresPermission(
            Manifest.permission.ACCESS_NETWORK_STATE
        ) {
            when (intent) {
                is TransferToCardContract.Intent.OnLoadData -> {
                    if (networkMonitor.checkConnection()) {
                        getCardsUseCase().onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                if (it.isNotEmpty()) {
                                    reduce { state.copy(fromCard = it[0]) }
                                    reduce { state.copy(cards = it) }
                                } else {
                                    postSideEffect(TransferToCardContract.SideEffect.ShowMessage("Card not found"))
                                    postSideEffect(TransferToCardContract.SideEffect.PopBack)
                                }
                            }.onFailure {
                                postSideEffect(TransferToCardContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(TransferToCardContract.SideEffect.NoConnection)
                    }
                }

                is TransferToCardContract.Intent.OnCheckCard -> {
                    if (networkMonitor.checkConnection()) {
                        checkCardUseCase(intent.cardNumber).onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                reduce { state.copy(foundName = it) }
                                reduce { state.copy(nameDialogState = true) }
                            }.onFailure {
                                postSideEffect(TransferToCardContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(TransferToCardContract.SideEffect.NoConnection)
                    }
                }

                is TransferToCardContract.Intent.OnTransfer -> {
                    if (networkMonitor.checkConnection()) {
                        initiateTransferUseCase(intent.cardId,intent.toCardNumber,intent.amount,"").onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                postSideEffect(TransferToCardContract.SideEffect.PinScreen)
                            }.onFailure {
                                postSideEffect(TransferToCardContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(TransferToCardContract.SideEffect.NoConnection)
                    }
                }

                is TransferToCardContract.Intent.OnChangeCurrent -> {
                    reduce { state.copy(fromCard = intent.cardData) }
                }

                is TransferToCardContract.Intent.OnCloseNameSheet ->{
                    reduce { state.copy(nameDialogState = false) }
                }

                is TransferToCardContract.Intent.OnOpenNameSheet ->{
                    reduce { state.copy(nameDialogState = true) }
                }
            }
        }
}

