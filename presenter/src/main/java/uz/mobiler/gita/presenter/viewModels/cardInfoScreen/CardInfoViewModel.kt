package uz.mobiler.gita.presenter.viewModels.cardInfoScreen

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
import uz.mobiler.gita.presenter.viewModels.homeScreen.HomeContract
import uz.mobiler.gita.usecase.DetachCardUseCase
import uz.mobiler.gita.usecase.GetCardsUseCase
import uz.mobiler.gita.usecase.GetExchangeUseCase
import uz.mobiler.gita.usecase.SetMainCardUseCase
import javax.inject.Inject

@HiltViewModel
class CardInfoViewModel @Inject constructor(
    private val detachCardUseCase: DetachCardUseCase,
    private val setMainCardUseCase: SetMainCardUseCase,
    private val networkMonitor: NetworkMonitor
) : CardInfoContract.ViewModel, ViewModel() {

    override val container =
        container<CardInfoContract.UiState, CardInfoContract.SideEffect>(CardInfoContract.UiState())

    override fun onEventDispatcher(intent: CardInfoContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is CardInfoContract.Intent.OnSetMain -> {
                if (networkMonitor.checkConnection()) {
                    setMainCardUseCase(intent.id).onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            postSideEffect(CardInfoContract.SideEffect.ShowMessage("Cart is set as main"))
                        }.onFailure {
                            postSideEffect(CardInfoContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                   postSideEffect(CardInfoContract.SideEffect.NoConnection)
                }
            }

            is CardInfoContract.Intent.OnDeleteCard -> {
                if (networkMonitor.checkConnection()) {
                    detachCardUseCase(intent.id).onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            postSideEffect(CardInfoContract.SideEffect.ShowMessage("Cart is deleted"))
                            postSideEffect(CardInfoContract.SideEffect.PopBack)
                        }.onFailure {
                            postSideEffect(CardInfoContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                    postSideEffect(CardInfoContract.SideEffect.NoConnection)
                }
            }
        }
    }
}

