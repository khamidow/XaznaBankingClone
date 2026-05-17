package uz.mobiler.gita.presenter.viewModels.paymentsScreen

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
import uz.mobiler.gita.core.models.PaymentData
import uz.mobiler.gita.presenter.util.NetworkMonitor
import uz.mobiler.gita.presenter.viewModels.transfers.TransfersContract
import uz.mobiler.gita.usecase.GetPaymentProvidersUseCase
import uz.mobiler.gita.usecase.KycStatusUseCase
import uz.mobiler.gita.usecase.MakePaymentUseCase
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val paymentProvidersUseCase: GetPaymentProvidersUseCase,
    private val makePaymentUseCase: MakePaymentUseCase,
    private val getKycStatusUseCase: KycStatusUseCase,
    private val networkMonitor: NetworkMonitor
) : PaymentsContract.ViewModel, ViewModel() {

    override val container =
        container<PaymentsContract.UiState, PaymentsContract.SideEffect>(PaymentsContract.UiState())

    private val allProviders = mutableListOf<PaymentData>()

    init {
        onEventDispatcher(PaymentsContract.Intent.OnLoadData)
    }

    override fun onEventDispatcher(intent: PaymentsContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is PaymentsContract.Intent.OnLoadData -> {
                if (networkMonitor.checkConnection()) {
                    paymentProvidersUseCase("").onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            val categories = mutableListOf<String>()
                            categories.add("ALL")
                            it.forEach {
                                if (categories.last() != it.category) {
                                    categories.add(it.category)
                                }
                            }
                            allProviders.addAll(it)
                            reduce { state.copy(currentCategory = "ALL") }
                            reduce { state.copy(categories = categories) }
                            reduce { state.copy(paymentProviders = allProviders) }
                        }.onFailure {
                            postSideEffect(PaymentsContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    postSideEffect(PaymentsContract.SideEffect.NoConnection)
                }
            }

            is PaymentsContract.Intent.OnSelectCategory -> {
                reduce { state.copy(currentCategory = intent.name) }

                val providers = allProviders.filter { intent.name == it.category }
                reduce { state.copy(paymentProviders = if (providers.isEmpty()) allProviders else providers) }
            }

            is PaymentsContract.Intent.OnCheckKcy -> {
                getKycStatusUseCase().onStart {
                    reduce { state.copy(loading = true) }
                }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                    it.onSuccess {
                        if (!it) {
                            postSideEffect(PaymentsContract.SideEffect.KycDialog)
                        }else{
                            postSideEffect(PaymentsContract.SideEffect.Navigate(intent.screen))
                        }
                    }.onFailure {
                        postSideEffect(PaymentsContract.SideEffect.ShowMessage(it.message.toString()))
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}

