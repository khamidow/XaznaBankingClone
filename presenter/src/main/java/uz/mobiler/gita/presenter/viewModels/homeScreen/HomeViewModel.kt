package uz.mobiler.gita.presenter.viewModels.homeScreen

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
class HomeViewModel @Inject constructor(
    private val getExchangeUseCase: GetExchangeUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val networkMonitor: NetworkMonitor
) : HomeContract.ViewModel, ViewModel() {

    override val container =
        container<HomeContract.UiState, HomeContract.SideEffect>(HomeContract.UiState())

    init {
        onEventDispatcher(HomeContract.Intent.OnLoadData)
    }

    override fun onEventDispatcher(intent: HomeContract.Intent) = intent @RequiresPermission(
        Manifest.permission.ACCESS_NETWORK_STATE
    ) {
        when (intent) {
            is HomeContract.Intent.OnLoadData -> {
                if (networkMonitor.checkConnection()) {
                    getCardsUseCase().onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            var sum = 0L
                            it.forEach {
                                sum += it.balance
                            }
                            reduce { state.copy(allSum = sum) }
                            reduce { state.copy(cards = it) }
                        }.onFailure {
                            postSideEffect(HomeContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                    getExchangeUseCase().onStart {
                        reduce { state.copy(loading = true) }
                    }.onCompletion { reduce { state.copy(loading = false) } }.onEach {
                        it.onSuccess {
                            reduce { state.copy(exchangeRates = it) }
                        }.onFailure {
                            postSideEffect(HomeContract.SideEffect.ShowMessage(it.message.toString()))
                        }
                    }.launchIn(viewModelScope)
                } else {
                   postSideEffect(HomeContract.SideEffect.NoConnection)
                }
            }
        }
    }
}

