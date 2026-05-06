package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface VerifyOtpUseCase {
    operator fun invoke(phone:String,otp:String): Flow<Result<Boolean>>
}