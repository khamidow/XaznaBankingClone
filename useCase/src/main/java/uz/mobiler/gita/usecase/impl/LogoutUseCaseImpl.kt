package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.AuthRepository
import uz.mobiler.gita.usecase.LogoutUseCase
import uz.mobiler.gita.usecase.SendOtpUseCase
import uz.mobiler.gita.usecase.SetPinUseCase
import uz.mobiler.gita.usecase.VerifyOtpUseCase
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(private val rep: AuthRepository) : LogoutUseCase {
    override fun invoke(): Flow<Result<String>> = flow {
        emit(rep.logout())
    }.catch {
        emit(Result.failure(Throwable(it.message.toString())))
    }.flowOn(Dispatchers.IO)
}