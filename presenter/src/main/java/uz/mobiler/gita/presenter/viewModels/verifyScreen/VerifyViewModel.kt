package uz.mobiler.gita.presenter.viewModels.verifyScreen

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import uz.mobiler.gita.presenter.util.NetworkMonitor
import uz.mobiler.gita.presenter.viewModels.phoneScreen.PhoneContract
import uz.mobiler.gita.usecase.SendOtpUseCase
import uz.mobiler.gita.usecase.VerifyOtpUseCase
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val sendOtpUseCase: SendOtpUseCase,
    private val networkMonitor: NetworkMonitor
) : VerifyContract.ViewModel, ViewModel() {

    override val container =
        container<VerifyContract.UiState, VerifyContract.SideEffect>(VerifyContract.UiState())

    private val isLoaded = MutableLiveData(false)

    fun load(phone: String) {
//        Log.d("TTT", "loadDataFun")
        if (isLoaded.value != true) {
            onEventDispatcher(VerifyContract.Intent.OnSendOtp(phone))
            isLoaded.value = true
        }
    }

    override fun onEventDispatcher(intent: VerifyContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is VerifyContract.Intent.OnVerifyOtp -> {
                if (networkMonitor.checkConnection()) {
                    verifyOtpUseCase(intent.phone, intent.code).onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach { it ->
                        it.onSuccess {
                            reduce { state.copy(success = true) }
                            reduce { state.copy(isNewUser = it) }
                        }.onFailure {
                            reduce { state.copy(success = false) }
                            postSideEffect(VerifyContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    reduce { state.copy(success = false) }
                    postSideEffect(VerifyContract.SideEffect.NoConnection)
                }
            }

            is VerifyContract.Intent.OnSendOtp -> {
                if (networkMonitor.checkConnection()) {
                    sendOtpUseCase(intent.phone).onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            reduce { state.copy(message = it) }
                        }.onFailure {
                            postSideEffect(VerifyContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    postSideEffect(VerifyContract.SideEffect.NoConnection)
                }
            }
        }
    }
}