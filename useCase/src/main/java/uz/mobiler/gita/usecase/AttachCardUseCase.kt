package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface AttachCardUseCase {
    operator fun invoke(number:String,bcg: String): Flow<Result<String>>
}