package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface SetPinUseCase {
    operator fun invoke(pin: String): Flow<Result<String>>
}