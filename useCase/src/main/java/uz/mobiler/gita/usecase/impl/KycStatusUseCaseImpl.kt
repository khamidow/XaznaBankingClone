package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.core.models.LoanData
import uz.mobiler.gita.entity.repository.KycRepository
import uz.mobiler.gita.entity.repository.LoansRepository
import uz.mobiler.gita.usecase.ApplyForLoanUseCase
import uz.mobiler.gita.usecase.KycStatusUseCase
import javax.inject.Inject

class KycStatusUseCaseImpl @Inject constructor(
    private val repository: KycRepository
) : KycStatusUseCase {
    override fun invoke(): Flow<Result<Boolean>> = flow {
        emit(repository.getKycStatus())
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}