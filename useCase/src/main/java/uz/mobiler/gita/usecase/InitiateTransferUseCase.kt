package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface InitiateTransferUseCase {
    suspend operator fun invoke(
        cardId: String,
        toCardNumber: String,
        amount: Long,
        description: String
    ): Flow<Result<Boolean>>
}