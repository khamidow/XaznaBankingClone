package uz.mobiler.gita.entity.repository.impl

import android.util.Log
import com.google.gson.Gson
import uz.mobiler.gita.entity.repository.KycRepository
import uz.mobiler.gita.entity.source.remote.api.KYCApi
import uz.mobiler.gita.entity.source.remote.request.KycRequest
import uz.mobiler.gita.entity.source.remote.response.OtpGeneralErrorResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KycRepositoryImpl @Inject constructor(
    private val kycApi: KYCApi,
    private val gson: Gson,
) : KycRepository {
    override suspend fun getKycStatus(): Result<Boolean> {
        val response = kycApi.getKycStatus()

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data!!
            if (data.status == "NOT_SUBMITTED") {
                Result.success(false)
            } else {
                Result.success(true)
            }
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }

    override suspend fun submitKycDocuments(
        passportSeries: String,
        passportNumber: String,
        birthDate: String,
        selfieBase64: String
    ): Result<Boolean> {
        val request = KycRequest(passportSeries, passportNumber, birthDate, selfieBase64.take(15))
        Log.d("KYC_BODY", Gson().toJson(request))
        val response = kycApi.submitKycDocuments(request)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data!!
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
