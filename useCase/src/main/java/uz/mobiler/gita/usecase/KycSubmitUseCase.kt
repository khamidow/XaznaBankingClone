package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface KycSubmitUseCase {
    operator fun invoke(
        passportSeries: String,
        passportNumber: String,
        birthDate: String,
        selfieBase64: String
    ): Flow<Result<Boolean>>
}