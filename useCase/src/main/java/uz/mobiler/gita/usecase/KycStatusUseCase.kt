package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface KycStatusUseCase {
    operator fun invoke(): Flow<Result<Boolean>>
}