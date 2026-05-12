package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface SetMainCardUseCase {
    operator fun invoke(id:String): Flow<Result<Boolean>>
}