package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface ConfirmOtpUseCase {
    suspend operator fun invoke(otp: String): Flow<Result<Boolean>>
}
