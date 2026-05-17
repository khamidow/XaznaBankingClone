package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface MakePaymentUseCase {

    suspend operator fun invoke(
        providerId: String,
        cardId: String,
        amount: Long,
        account:String
    ): Flow<Result<Boolean>>
}