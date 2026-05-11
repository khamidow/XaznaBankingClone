package uz.mobiler.gita.presenter.viewModels.addCardScreen

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
import uz.mobiler.gita.usecase.AttachCardUseCase
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val addCardUseCase: AttachCardUseCase,
    private val networkMonitor: NetworkMonitor
) : AddCardContract.ViewModel, ViewModel() {

    override val container =
        container<AddCardContract.UiState, AddCardContract.SideEffect>(AddCardContract.UiState())

    override fun onEventDispatcher(intent: AddCardContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is AddCardContract.Intent.OnAddCard -> {
                if (networkMonitor.checkConnection()) {
                    addCardUseCase(intent.cardNumber).onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            postSideEffect(AddCardContract.SideEffect.ShowMessage("Card is added"))
                            reduce { state.copy(message = it) }
                        }.onFailure {
                            postSideEffect(AddCardContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                   postSideEffect(AddCardContract.SideEffect.NoConnection)
                }
            }
        }
    }
}

