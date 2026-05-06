package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow

interface SendOtpUseCase {
    operator fun invoke(phone:String): Flow<Result<String>>
}