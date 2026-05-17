package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.core.models.PaymentData
import uz.mobiler.gita.entity.repository.PaymentsRepository
import uz.mobiler.gita.usecase.GetPaymentProvidersUseCase
import javax.inject.Inject

class GetPaymentProvidersUseCaseImpl @Inject constructor(
    private val repository: PaymentsRepository
) : GetPaymentProvidersUseCase {

    override suspend fun invoke(
        category: String
    ): Flow<Result<List<PaymentData>>> = flow {

        emit(repository.getPaymentProviders(category))

    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}