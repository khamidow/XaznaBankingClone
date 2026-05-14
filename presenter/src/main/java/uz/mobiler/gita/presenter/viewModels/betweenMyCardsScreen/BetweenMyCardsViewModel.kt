package uz.mobiler.gita.presenter.viewModels.betweenMyCardsScreen

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
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.presenter.util.NetworkMonitor
import uz.mobiler.gita.usecase.CheckCardUseCase
import uz.mobiler.gita.usecase.ConfirmOtpUseCase
import uz.mobiler.gita.usecase.ConfirmPinUseCase
import uz.mobiler.gita.usecase.GetCardsUseCase
import uz.mobiler.gita.usecase.InitiateTransferUseCase
import javax.inject.Inject

@HiltViewModel
class BetweenMyCardsViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val initiateTransferUseCase: InitiateTransferUseCase,
    private val confirmPinUseCase: ConfirmPinUseCase,
    private val confirmOtpUseCase: ConfirmOtpUseCase,
    private val checkCardUseCase: CheckCardUseCase,
    private val networkMonitor: NetworkMonitor
) : BetweenMyCardsContract.ViewModel, ViewModel() {

    override val container =
        container<BetweenMyCardsContract.UiState, BetweenMyCardsContract.SideEffect>(
            BetweenMyCardsContract.UiState()
        )

    init {
        onEventDispatcher(BetweenMyCardsContract.Intent.OnLoadData)
    }

    override fun onEventDispatcher(intent: BetweenMyCardsContract.Intent) =
        intent @RequiresPermission(
            Manifest.permission.ACCESS_NETWORK_STATE
        ) {
            when (intent) {
                is BetweenMyCardsContract.Intent.OnLoadData -> {
                    if (networkMonitor.checkConnection()) {
                        getCardsUseCase().onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                if (it.isNotEmpty()) {
                                    if (it.size >= 2) {
                                        reduce { state.copy(fromCard = it[0]) }
                                        reduce { state.copy(toCard = it[1]) }
                                        reduce { state.copy(cards = it) }
                                    } else {
                                        postSideEffect(
                                            BetweenMyCardsContract.SideEffect.ShowMessage(
                                                "Not enough cards"
                                            )
                                        )
                                        postSideEffect(BetweenMyCardsContract.SideEffect.PopBack)
                                    }
                                } else {
                                    postSideEffect(BetweenMyCardsContract.SideEffect.ShowMessage("Card not found"))
                                    postSideEffect(BetweenMyCardsContract.SideEffect.PopBack)
                                }
                            }.onFailure {
                                postSideEffect(BetweenMyCardsContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(BetweenMyCardsContract.SideEffect.NoConnection)
                    }
                }

                is BetweenMyCardsContract.Intent.OnTransfer -> {
                    if (networkMonitor.checkConnection()) {
                        initiateTransferUseCase(
                            intent.cardId,
                            intent.toCardNumber,
                            intent.amount,
                            ""
                        ).onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                if (intent.toCardNumber.isEmpty()) {
                                    postSideEffect(BetweenMyCardsContract.SideEffect.ShowMessage("This feature doesn't work due to some issue"))
                                } else {
                                    postSideEffect(BetweenMyCardsContract.SideEffect.PinScreen)
                                }
                            }.onFailure {
                                postSideEffect(BetweenMyCardsContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(BetweenMyCardsContract.SideEffect.NoConnection)
                    }
                }

                is BetweenMyCardsContract.Intent.OnChangeCurrent1 -> {
                    reduce { state.copy(fromCard = intent.cardData) }
                }

                is BetweenMyCardsContract.Intent.OnChangeCurrent2 -> {
                    reduce { state.copy(toCard = intent.cardData) }
                }

                is BetweenMyCardsContract.Intent.OnReplacePlaces -> {
                    val tempState = state.toCard!!
                    val tempCard = state.toCard?.copy()
                    reduce { state.copy(toCard = state.fromCard) }
                    reduce { state.copy(fromCard = tempCard) }
                }
            }
        }
}

