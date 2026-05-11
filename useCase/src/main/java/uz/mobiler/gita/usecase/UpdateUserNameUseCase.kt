package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface UpdateUserNameUseCase {
    operator fun invoke(name:String): Flow<Result<Boolean>>
}