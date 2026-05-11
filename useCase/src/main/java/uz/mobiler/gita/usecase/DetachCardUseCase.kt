package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.core.models.UserData
import uz.mobiler.gita.entity.source.remote.response.CardDataResponse

interface DetachCardUseCase {
    operator fun invoke(id:String): Flow<Result<Boolean>>
}