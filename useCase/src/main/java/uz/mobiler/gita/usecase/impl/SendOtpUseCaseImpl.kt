package uz.mobiler.gita.usecase.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.AuthRepository
import uz.mobiler.gita.usecase.SendOtpUseCase
import javax.inject.Inject

class SendOtpUseCaseImpl @Inject constructor(private val rep: AuthRepository) : SendOtpUseCase {
    override fun invoke(phone: String): Flow<Result<String>> = flow {
        Log.d("TTT","useCase-phoneOtp")
        emit(rep.sendOtp(phone))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}