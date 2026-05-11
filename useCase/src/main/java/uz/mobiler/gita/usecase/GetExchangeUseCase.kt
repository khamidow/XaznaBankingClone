package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow
import uz.mobiler.gita.core.models.ExchangeData

interface GetExchangeUseCase {
    operator fun invoke(): Flow<Result<List<ExchangeData>>>
}