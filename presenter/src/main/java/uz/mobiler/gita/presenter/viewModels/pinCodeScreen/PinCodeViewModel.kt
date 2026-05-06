package uz.mobiler.gita.presenter.viewModels.pinCodeScreen

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
import uz.mobiler.gita.usecase.SendOtpUseCase
import uz.mobiler.gita.usecase.SetPinUseCase
import javax.inject.Inject

@HiltViewModel
class PinCodeViewModel @Inject constructor(
    private val setPinUseCase: SetPinUseCase,
    private val networkMonitor: NetworkMonitor
) : PinCodeContract.ViewModel, ViewModel() {

    override val container =
        container<PinCodeContract.UiState, PinCodeContract.SideEffect>(PinCodeContract.UiState())

    override fun onEventDispatcher(intent: PinCodeContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is PinCodeContract.Intent.OnSetPin -> {
                if (networkMonitor.checkConnection()) {
                    reduce { state.copy(noNetworkConnection = false) }
                    setPinUseCase(intent.pin).onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            postSideEffect(PinCodeContract.SideEffect.NavigateMain)
                            reduce { state.copy(message = it) }
                        }.onFailure {
                            postSideEffect(PinCodeContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    reduce { state.copy(noNetworkConnection = true) }
                }
            }
        }
    }
}

