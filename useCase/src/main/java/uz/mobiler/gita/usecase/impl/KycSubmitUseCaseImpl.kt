package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.entity.repository.KycRepository
import uz.mobiler.gita.usecase.KycSubmitUseCase
import javax.inject.Inject

class KycSubmitUseCaseImpl @Inject constructor(
    private val repository: KycRepository
) : KycSubmitUseCase {
    override fun invoke(
        passportSeries: String,
        passportNumber: String,
        birthDate: String,
        selfieBase64: String
    ): Flow<Result<Boolean>> = flow {
        emit(repository.submitKycDocuments(passportSeries, passportNumber, birthDate, selfieBase64))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}