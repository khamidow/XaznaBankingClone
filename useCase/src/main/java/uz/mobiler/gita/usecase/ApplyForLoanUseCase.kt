package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow
import uz.mobiler.gita.core.models.LoanData

interface ApplyForLoanUseCase {

    operator fun invoke(
        amount: Long,
        termMonths: Long,
        cardId: String
    ): Flow<Result<LoanData>>
}