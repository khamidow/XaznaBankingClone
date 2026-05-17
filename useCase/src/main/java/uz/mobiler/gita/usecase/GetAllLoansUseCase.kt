package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow
import uz.mobiler.gita.core.models.LoanData

interface GetAllLoansUseCase {

    operator fun invoke(): Flow<Result<List<LoanData>>>
}