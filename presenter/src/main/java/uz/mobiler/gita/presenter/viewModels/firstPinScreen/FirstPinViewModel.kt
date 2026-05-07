package uz.mobiler.gita.presenter.viewModels.firstPinScreen

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
import uz.mobiler.gita.usecase.LogoutUseCase
import javax.inject.Inject

@HiltViewModel
class FirstPinViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val networkMonitor: NetworkMonitor
) : FirstPinContract.ViewModel, ViewModel() {

    override val container =
        container<FirstPinContract.UiState, FirstPinContract.SideEffect>(FirstPinContract.UiState())

    override fun onEventDispatcher(intent: FirstPinContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is FirstPinContract.Intent.OnLogOut -> {
                if (networkMonitor.checkConnection()) {
                    logoutUseCase().onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            postSideEffect(FirstPinContract.SideEffect.NavigateLanguage)
                            reduce { state.copy(message = it) }
                        }.onFailure {
                            postSideEffect(FirstPinContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    postSideEffect(FirstPinContract.SideEffect.NoConnection)
                }
            }
        }
    }
}

