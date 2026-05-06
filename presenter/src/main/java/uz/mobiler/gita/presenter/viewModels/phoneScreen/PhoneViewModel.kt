package uz.mobiler.gita.presenter.viewModels.phoneScreen

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
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val networkMonitor: NetworkMonitor
) : PhoneContract.ViewModel, ViewModel() {

    override val container =
        container<PhoneContract.UiState, PhoneContract.SideEffect>(PhoneContract.UiState())

    override fun onEventDispatcher(intent: PhoneContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is PhoneContract.Intent.OnSendOtp -> {
                if (networkMonitor.checkConnection()) {
                    reduce { state.copy(noNetworkConnection = false) }
                    sendOtpUseCase(intent.phone).onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            postSideEffect(PhoneContract.SideEffect.NavigateVerify)
                            reduce { state.copy(message = it) }
                        }.onFailure {
                            postSideEffect(PhoneContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    reduce { state.copy(noNetworkConnection = true) }
                }
            }
        }
    }
}

