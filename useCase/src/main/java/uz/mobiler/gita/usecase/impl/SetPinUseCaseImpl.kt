package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.AuthRepository
import uz.mobiler.gita.usecase.SendOtpUseCase
import uz.mobiler.gita.usecase.SetPinUseCase
import uz.mobiler.gita.usecase.VerifyOtpUseCase
import javax.inject.Inject

class SetPinUseCaseImpl @Inject constructor(private val rep: AuthRepository) : SetPinUseCase {
    override fun invoke(pin: String): Flow<Result<String>> = flow {
        emit(rep.setPin(pin))
    }.catch {
        emit(Result.failure(Throwable(it.message.toString())))
    }.flowOn(Dispatchers.IO)
}