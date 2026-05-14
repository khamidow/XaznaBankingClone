package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface ConfirmPinUseCase {
    suspend operator fun invoke(pin: String): Flow<Result<Boolean>>
}
