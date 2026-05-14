package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.TransfersRepository
import uz.mobiler.gita.usecase.ConfirmOtpUseCase
import javax.inject.Inject

class ConfirmOtpUseCaseImpl @Inject constructor(
    private val repository: TransfersRepository
) : ConfirmOtpUseCase {

    override suspend fun invoke(otp: String): Flow<Result<Boolean>> = flow {
        emit(repository.confirmOtp(otp))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}