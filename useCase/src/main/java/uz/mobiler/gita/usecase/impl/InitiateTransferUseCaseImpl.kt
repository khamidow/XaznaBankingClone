package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.TransfersRepository
import uz.mobiler.gita.usecase.InitiateTransferUseCase
import javax.inject.Inject


class InitiateTransferUseCaseImpl @Inject constructor(
    private val repository: TransfersRepository
) : InitiateTransferUseCase {

    override suspend fun invoke(
        cardId: String,
        toCardNumber: String,
        amount: Long,
        description: String
    ): Flow<Result<Boolean>> = flow {
        emit(
            repository.initiateTransfer(
                cardId,
                toCardNumber,
                amount,
                description
            )
        )
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}