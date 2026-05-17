package uz.mobiler.gita.entity.repository.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import uz.mobiler.gita.core.models.PaymentData
import uz.mobiler.gita.entity.repository.PaymentsRepository
import uz.mobiler.gita.entity.source.local.TokenManager
import uz.mobiler.gita.entity.source.remote.api.PaymentsApi
import uz.mobiler.gita.entity.source.remote.request.MakePaymentRequest
import uz.mobiler.gita.entity.source.remote.response.OtpGeneralErrorResponse
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
    private val paymentsApi: PaymentsApi,
    private val gson: Gson,
    private val pref: SharedPreferences
) : PaymentsRepository {

    override suspend fun getPaymentProviders(category: String): Result<List<PaymentData>> {
        val response = paymentsApi.getPaymentProviders(category)

        return if (response.isSuccessful && response.body() != null) {
            val data =
                response.body()?.data?.map { PaymentData(it.id, it.name, it.category, it.logoUrl) }
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

    override suspend fun makePayment(
        providerId: String,
        cardId: String,
        amount: Long,
        account: String
    ): Result<Boolean> {
        val accountPhoneNumber = pref.getString("phone_number", "")?.drop(1)!!
        val request = MakePaymentRequest(
            providerId,
            cardId,
            amount,
            if (account.isEmpty()) accountPhoneNumber else account
        )
        val response = paymentsApi.makePayment(request)

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