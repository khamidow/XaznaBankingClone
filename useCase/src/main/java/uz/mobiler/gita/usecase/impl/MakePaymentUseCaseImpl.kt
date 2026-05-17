package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.PaymentsRepository
import uz.mobiler.gita.usecase.MakePaymentUseCase
import javax.inject.Inject

class MakePaymentUseCaseImpl @Inject constructor(
    private val repository: PaymentsRepository
) : MakePaymentUseCase {

    override suspend fun invoke(
        providerId: String,
        cardId: String,
        amount: Long,
        account:String
    ): Flow<Result<Boolean>> = flow {

        emit(
            repository.makePayment(
                providerId,
                cardId,
                amount,
                account
            )
        )

    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}