package uz.mobiler.gita.presenter.viewModels.addLoanScreen

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
import uz.mobiler.gita.presenter.viewModels.transferToCardScreen.TransferToCardContract
import uz.mobiler.gita.usecase.ApplyForLoanUseCase
import uz.mobiler.gita.usecase.CheckCardUseCase
import uz.mobiler.gita.usecase.ConfirmOtpUseCase
import uz.mobiler.gita.usecase.ConfirmPinUseCase
import uz.mobiler.gita.usecase.GetCardsUseCase
import uz.mobiler.gita.usecase.InitiateTransferUseCase
import javax.inject.Inject

@HiltViewModel
class AddLoanContractViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val applyForLoanUseCase: ApplyForLoanUseCase,
    private val networkMonitor: NetworkMonitor
) : AddLoanContract.ViewModel, ViewModel() {

    override val container =
        container<AddLoanContract.UiState, AddLoanContract.SideEffect>(
            AddLoanContract.UiState()
        )

    init {
        onEventDispatcher(AddLoanContract.Intent.OnLoadData)
    }

    override fun onEventDispatcher(intent: AddLoanContract.Intent) =
        intent @RequiresPermission(
            Manifest.permission.ACCESS_NETWORK_STATE
        ) {
            when (intent) {
                is AddLoanContract.Intent.OnLoadData -> {
                    if (networkMonitor.checkConnection()) {
                        getCardsUseCase().onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                if (it.isNotEmpty()) {
                                    reduce { state.copy(fromCard = it[0]) }
                                    reduce { state.copy(cards = it) }
                                } else {
                                    postSideEffect(AddLoanContract.SideEffect.ShowMessage("Card not found"))
                                    postSideEffect(AddLoanContract.SideEffect.PopBack)
                                }
                            }.onFailure {
                                postSideEffect(AddLoanContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(AddLoanContract.SideEffect.NoConnection)
                    }
                }

                is AddLoanContract.Intent.OnAddPayment -> {
                    if (networkMonitor.checkConnection()) {
                        applyForLoanUseCase(intent.amount, intent.months, intent.cardId).onStart {
                            reduce { state.copy(loading = true) }
                        }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                            it.onSuccess {
                                postSideEffect(AddLoanContract.SideEffect.ShowMessage("Loan applied"))
                                postSideEffect(AddLoanContract.SideEffect.PopBack)
                            }.onFailure {
                                postSideEffect(AddLoanContract.SideEffect.ShowMessage(it.message.toString()))
                            }
                        }.launchIn(viewModelScope)
                    } else {
                        postSideEffect(AddLoanContract.SideEffect.NoConnection)
                    }
                }

                is AddLoanContract.Intent.OnChangeCurrent -> {
                    reduce { state.copy(fromCard = intent.cardData) }
                }
            }
        }
}

