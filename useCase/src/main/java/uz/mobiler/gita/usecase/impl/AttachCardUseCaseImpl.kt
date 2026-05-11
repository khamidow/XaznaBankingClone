package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.CardsRepository
import uz.mobiler.gita.usecase.AttachCardUseCase
import javax.inject.Inject

class AttachCardUseCaseImpl @Inject constructor(private val rep: CardsRepository) : AttachCardUseCase {
    override fun invoke(number:String): Flow<Result<String>> = flow {
        emit(rep.attachCard(number))
    }.catch {
        emit(Result.failure(Throwable(it.message.toString())))
    }.flowOn(Dispatchers.IO)
}