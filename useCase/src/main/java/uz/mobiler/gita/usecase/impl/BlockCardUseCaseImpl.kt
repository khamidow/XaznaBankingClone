package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.CardsRepository
import uz.mobiler.gita.usecase.BlockCardUseCase
import javax.inject.Inject

class BlockCardUseCaseImpl @Inject constructor(private val rep: CardsRepository) : BlockCardUseCase {
    override fun invoke(id: String): Flow<Result<Boolean>> = flow {
        emit(rep.blockCard(id))
    }.catch {
        emit(Result.failure(Throwable(it.message.toString())))
    }.flowOn(Dispatchers.IO)
}