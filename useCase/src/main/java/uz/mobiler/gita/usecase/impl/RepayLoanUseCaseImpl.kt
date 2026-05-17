package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.LoansRepository
import uz.mobiler.gita.usecase.RepayLoanUseCase
import javax.inject.Inject

class RepayLoanUseCaseImpl @Inject constructor(
    private val repository: LoansRepository
) : RepayLoanUseCase {

    override fun invoke(
        id: String,
        cardId: String,
        amount: Long
    ): Flow<Result<Boolean>> = flow {

        emit(
            repository.repayLoan(
                id,
                cardId,
                amount
            )
        )

    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}