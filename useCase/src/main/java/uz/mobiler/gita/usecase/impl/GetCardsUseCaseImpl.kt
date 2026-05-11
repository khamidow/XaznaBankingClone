package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.entity.repository.CardsRepository
import uz.mobiler.gita.entity.source.remote.response.CardDataResponse
import uz.mobiler.gita.usecase.GetCardsUseCase
import javax.inject.Inject

class GetCardsUseCaseImpl @Inject constructor(private val rep: CardsRepository) : GetCardsUseCase {
    override fun invoke(): Flow<Result<List<CardData>>> = flow {
        emit(rep.getCards())
    }.catch {
        emit(Result.failure(Throwable(it.message.toString())))
    }.flowOn(Dispatchers.IO)
}