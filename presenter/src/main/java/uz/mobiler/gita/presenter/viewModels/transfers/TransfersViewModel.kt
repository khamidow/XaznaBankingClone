package uz.mobiler.gita.presenter.viewModels.transfers

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
import uz.mobiler.gita.core.models.ExchangeData
import uz.mobiler.gita.presenter.util.NetworkMonitor
import uz.mobiler.gita.presenter.viewModels.homeScreen.HomeContract
import uz.mobiler.gita.usecase.GetCardsUseCase
import uz.mobiler.gita.usecase.GetExchangeUseCase
import uz.mobiler.gita.usecase.KycStatusUseCase
import javax.inject.Inject

@HiltViewModel
class TransfersViewModel @Inject constructor(
    private val getKycStatusUseCase: KycStatusUseCase,
    private val networkMonitor: NetworkMonitor
) : TransfersContract.ViewModel, ViewModel() {

    override val container =
        container<TransfersContract.UiState, TransfersContract.SideEffect>(TransfersContract.UiState())

    override fun onEventDispatcher(intent: TransfersContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {

            is TransfersContract.Intent.OnCheckKcy -> {
                getKycStatusUseCase().onStart {
                    reduce { state.copy(loading = true) }
                }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                    it.onSuccess {
                        if (!it) {
                            postSideEffect(TransfersContract.SideEffect.KycDialog)
                        }else{
                            postSideEffect(TransfersContract.SideEffect.Navigate(intent.screen))
                        }
                    }.onFailure {
                        postSideEffect(TransfersContract.SideEffect.ShowMessage(it.message.toString()))
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}

