package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface CheckCardUseCase {
    suspend operator fun invoke(cardNumber: String): Flow<Result<String>>
}