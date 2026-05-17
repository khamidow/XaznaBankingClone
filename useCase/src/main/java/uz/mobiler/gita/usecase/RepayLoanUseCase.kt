package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface RepayLoanUseCase {

    operator fun invoke(
        id: String,
        cardId: String,
        amount: Long
    ): Flow<Result<Boolean>>
}