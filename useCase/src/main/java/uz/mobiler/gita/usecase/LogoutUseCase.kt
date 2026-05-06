package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface LogoutUseCase {
    operator fun invoke(): Flow<Result<String>>
}