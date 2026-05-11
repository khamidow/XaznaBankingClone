package uz.mobiler.gita.presenter.viewModels.profileDetailScreen

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
import uz.mobiler.gita.usecase.GetUserInfoUseCase
import uz.mobiler.gita.usecase.UpdateUserNameUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val networkMonitor: NetworkMonitor
) : ProfileDetailContract.ViewModel, ViewModel() {

    override val container =
        container<ProfileDetailContract.UiState, ProfileDetailContract.SideEffect>(
            ProfileDetailContract.UiState())

    override fun onEventDispatcher(intent: ProfileDetailContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is ProfileDetailContract.Intent.OnUpdateName -> {
                if (networkMonitor.checkConnection()) {
                    updateUserNameUseCase(intent.name).onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            postSideEffect(ProfileDetailContract.SideEffect.PopBack)
                        }.onFailure {
                            postSideEffect(ProfileDetailContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                   postSideEffect(ProfileDetailContract.SideEffect.NoConnection)
                }
            }
        }
    }
}

