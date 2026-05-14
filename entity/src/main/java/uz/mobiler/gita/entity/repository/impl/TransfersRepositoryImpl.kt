package uz.mobiler.gita.entity.repository.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.entity.repository.TransfersRepository
import uz.mobiler.gita.entity.source.local.TokenManager
import uz.mobiler.gita.entity.source.remote.api.TransfersApi
import uz.mobiler.gita.entity.source.remote.request.TransferConfirmOtpRequest
import uz.mobiler.gita.entity.source.remote.request.TransferConfirmPinRequest
import uz.mobiler.gita.entity.source.remote.request.TransferRequest
import uz.mobiler.gita.entity.source.remote.response.OtpGeneralErrorResponse
import javax.inject.Inject

class TransfersRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val transfersApi: TransfersApi,
    private val gson: Gson,
    private val pref: SharedPreferences
) : TransfersRepository {

    override suspend fun initiateTransfer(
        cardId: String,
        toCardNumber: String,
        amount: Long,
        description: String
    ): Result<Boolean> {
        val pin = pref.getString("pin_code", "").toString()
        val request = TransferRequest(cardId, toCardNumber, amount, pin, description)
        val response = transfersApi.initiateTransfer(request)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data!!
            pref.edit().putString("confirm_token",data.confirmToken).apply()
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

    override suspend fun confirmPin(pin: String): Result<Boolean> {
        val confirmToken = pref.getString("confirm_token","").toString()
        val request = TransferConfirmPinRequest(confirmToken,pin)
        val response = transfersApi.confirmPin(request)

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

    override suspend fun confirmOtp(otp: String): Result<Boolean> {
        val confirmToken = pref.getString("confirm_token","").toString()
        val request = TransferConfirmOtpRequest(confirmToken,otp)
        val response = transfersApi.confirmOtp(request)

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

    override suspend fun checkCard(cardNumber: String): Result<String> {
        val response = transfersApi.checkCard(cardNumber)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data!!
            Result.success(data.ownerName)
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