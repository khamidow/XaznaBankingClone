package uz.mobiler.gita.presenter.viewModels.cardsScreen

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
import uz.mobiler.gita.usecase.GetCardsUseCase
import uz.mobiler.gita.usecase.GetExchangeUseCase
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val networkMonitor: NetworkMonitor
) : CardsContract.ViewModel, ViewModel() {

    override val container =
        container<CardsContract.UiState, CardsContract.SideEffect>(CardsContract.UiState())

    init {
        onEventDispatcher(CardsContract.Intent.OnLoadData)
    }

    override fun onEventDispatcher(intent: CardsContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is CardsContract.Intent.OnLoadData -> {
                if (networkMonitor.checkConnection()) {
                    getCardsUseCase().onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            val mainCard = it.find { it.isMain == true }
                            reduce { state.copy(mainCard = mainCard) }
                            reduce { state.copy(cards = it) }
                        }.onFailure {
                            postSideEffect(CardsContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                   postSideEffect(CardsContract.SideEffect.NoConnection)
                }
            }
        }
    }
}

