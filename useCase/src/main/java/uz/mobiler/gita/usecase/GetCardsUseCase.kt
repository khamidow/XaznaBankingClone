package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow
import uz.mobiler.gita.core.models.CardData

interface GetCardsUseCase {
    operator fun invoke(): Flow<Result<List<CardData>>>
}