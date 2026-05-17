package uz.mobiler.gita.entity.repository

import uz.mobiler.gita.core.models.LoanData

interface LoansRepository {
    suspend fun getAllLoans(): Result<List<LoanData>>
    suspend fun applyForLoan(amount: Long, termMonths: Long, cardId: String): Result<LoanData>
    suspend fun repayLoan(id: String, cardId: String, amount: Long): Result<Boolean>
}