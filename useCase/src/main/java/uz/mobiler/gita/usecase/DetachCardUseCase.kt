package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface DetachCardUseCase {
    operator fun invoke(id:String): Flow<Result<Boolean>>
}