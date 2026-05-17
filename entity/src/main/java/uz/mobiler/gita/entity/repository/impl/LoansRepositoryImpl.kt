package uz.mobiler.gita.entity.repository.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.core.models.LoanData
import uz.mobiler.gita.core.models.PaymentData
import uz.mobiler.gita.entity.repository.LoansRepository
import uz.mobiler.gita.entity.repository.PaymentsRepository
import uz.mobiler.gita.entity.repository.TransfersRepository
import uz.mobiler.gita.entity.source.local.TokenManager
import uz.mobiler.gita.entity.source.remote.api.LoansApi
import uz.mobiler.gita.entity.source.remote.api.PaymentsApi
import uz.mobiler.gita.entity.source.remote.api.TransfersApi
import uz.mobiler.gita.entity.source.remote.request.LoanRepayRequest
import uz.mobiler.gita.entity.source.remote.request.LoanRequest
import uz.mobiler.gita.entity.source.remote.request.MakePaymentRequest
import uz.mobiler.gita.entity.source.remote.request.TransferConfirmOtpRequest
import uz.mobiler.gita.entity.source.remote.request.TransferConfirmPinRequest
import uz.mobiler.gita.entity.source.remote.request.TransferRequest
import uz.mobiler.gita.entity.source.remote.response.OtpGeneralErrorResponse
import javax.inject.Inject

class LoansRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val loansApi: LoansApi,
    private val gson: Gson,
    private val pref: SharedPreferences
) : LoansRepository {

    override suspend fun getAllLoans(): Result<List<LoanData>> {
        val response = loansApi.getAllLoans()

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data?.map {
                LoanData(
                    it.id,
                    it.totalAmount,
                    it.remaining,
                    it.monthlyPayment,
                    it.termMonths,
                    it.nextDueDate,
                    it.status
                )
            }
            Result.success(data!!)
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }

    override suspend fun applyForLoan(
        amount: Long,
        termMonths: Long,
        cardId: String
    ): Result<LoanData> {
        val request = LoanRequest(amount, termMonths, cardId)
        val response = loansApi.applyForLoan(request)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data!!
            Result.success(
                LoanData(
                    data.id,
                    data.totalAmount,
                    data.remaining,
                    data.monthlyPayment,
                    data.termMonths,
                    data.nextDueDate,
                    data.status
                )
            )
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }

    override suspend fun repayLoan(
        id: String,
        cardId: String,
        amount: Long
    ): Result<Boolean> {
        val request = LoanRepayRequest(cardId,amount)
        val response = loansApi.repayLoan(id,request)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data
            Result.success(true)
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }
}