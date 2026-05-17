package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.core.models.LoanData
import uz.mobiler.gita.entity.repository.LoansRepository
import uz.mobiler.gita.usecase.ApplyForLoanUseCase
import javax.inject.Inject

class ApplyForLoanUseCaseImpl @Inject constructor(
    private val repository: LoansRepository
) : ApplyForLoanUseCase {

    override fun invoke(
        amount: Long,
        termMonths: Long,
        cardId: String
    ): Flow<Result<LoanData>> = flow {

        emit(
            repository.applyForLoan(
                amount,
                termMonths,
                cardId
            )
        )

    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}