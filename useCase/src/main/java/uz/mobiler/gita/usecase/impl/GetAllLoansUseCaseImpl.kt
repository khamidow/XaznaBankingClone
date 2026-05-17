package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.core.models.LoanData
import uz.mobiler.gita.entity.repository.LoansRepository
import uz.mobiler.gita.usecase.GetAllLoansUseCase
import javax.inject.Inject

class GetAllLoansUseCaseImpl @Inject constructor(
    private val repository: LoansRepository
) : GetAllLoansUseCase {

    override fun invoke(): Flow<Result<List<LoanData>>> = flow {

        emit(repository.getAllLoans())

    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}