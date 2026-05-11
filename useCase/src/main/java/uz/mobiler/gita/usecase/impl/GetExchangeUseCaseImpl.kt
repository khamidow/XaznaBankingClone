package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.core.models.ExchangeData
import uz.mobiler.gita.entity.repository.ExchangeRepository
import uz.mobiler.gita.usecase.GetExchangeUseCase
import javax.inject.Inject

class GetExchangeUseCaseImpl @Inject constructor(private val rep: ExchangeRepository) : GetExchangeUseCase {
    override fun invoke(): Flow<Result<List<ExchangeData>>> = flow {
        emit(rep.getExchangeRates())
    }.catch {
        emit(Result.failure(Throwable(it.message.toString())))
    }.flowOn(Dispatchers.IO)
}