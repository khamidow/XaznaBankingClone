package uz.mobiler.gita.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SessionEvent {
    private val _event = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val event: SharedFlow<Unit> = _event.asSharedFlow()

    fun logout() {
        _event.tryEmit(Unit)
    }
}