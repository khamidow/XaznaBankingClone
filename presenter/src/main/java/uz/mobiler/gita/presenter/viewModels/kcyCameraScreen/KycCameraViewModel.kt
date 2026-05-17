package uz.mobiler.gita.presenter.viewModels.kcyCameraScreen

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
import uz.mobiler.gita.usecase.KycSubmitUseCase
import javax.inject.Inject

@HiltViewModel
class KycCameraViewModel @Inject constructor(
    private val kycSubmitUseCase: KycSubmitUseCase,
    private val networkMonitor: NetworkMonitor
) : KycCameraContract.ViewModel, ViewModel() {

    override val container =
        container<KycCameraContract.UiState, KycCameraContract.SideEffect>(KycCameraContract.UiState())

    override fun onEventDispatcher(intent: KycCameraContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is KycCameraContract.Intent.OnSubmitKyc -> {
                if (networkMonitor.checkConnection()) {
                    kycSubmitUseCase(
                        intent.passportSeries,
                        intent.passportNumber,
                        intent.birthDate,
                        intent.selfieBase64
                    ).onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            postSideEffect(KycCameraContract.SideEffect.ShowMessage("Passed identification successfully"))
                            postSideEffect(KycCameraContract.SideEffect.NavigateToHome)
                        }.onFailure {
                            postSideEffect(KycCameraContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    postSideEffect(KycCameraContract.SideEffect.NoConnection)
                }
            }
        }
    }
}

