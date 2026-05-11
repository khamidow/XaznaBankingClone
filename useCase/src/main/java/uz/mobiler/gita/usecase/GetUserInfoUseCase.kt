package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow
import uz.mobiler.gita.core.models.UserData

interface GetUserInfoUseCase {
    operator fun invoke(): Flow<Result<UserData>>
}