package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.TransfersRepository
import uz.mobiler.gita.usecase.ConfirmPinUseCase
import javax.inject.Inject

class ConfirmPinUseCaseImpl @Inject constructor(
    private val repository: TransfersRepository
) : ConfirmPinUseCase {

    override suspend fun invoke(pin: String): Flow<Result<Boolean>> = flow {
        emit(repository.confirmPin(pin))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}