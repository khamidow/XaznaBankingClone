package uz.mobiler.gita.presenter.viewModels.profileScreen

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
import uz.mobiler.gita.presenter.viewModels.firstPinScreen.FirstPinContract
import uz.mobiler.gita.usecase.ConfirmPinUseCase
import uz.mobiler.gita.usecase.LogoutUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val networkMonitor: NetworkMonitor
) : ProfileContract.ViewModel, ViewModel() {

    override val container =
        container<ProfileContract.UiState, ProfileContract.SideEffect>(ProfileContract.UiState())

    override fun onEventDispatcher(intent: ProfileContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is ProfileContract.Intent.OnLogOut -> {
                if (networkMonitor.checkConnection()) {
                    logoutUseCase().onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            postSideEffect(ProfileContract.SideEffect.NavigateLanguage)
                            reduce { state.copy(message = it) }
                        }.onFailure {
                            postSideEffect(ProfileContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    postSideEffect(ProfileContract.SideEffect.NoConnection)
                }
            }
        }
    }
}

