package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.TransfersRepository
import uz.mobiler.gita.usecase.CheckCardUseCase
import javax.inject.Inject

class CheckCardUseCaseImpl @Inject constructor(
    private val repository: TransfersRepository
) : CheckCardUseCase {

    override suspend fun invoke(cardNumber: String): Flow<Result<String>> = flow {
        emit(repository.checkCard(cardNumber))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}