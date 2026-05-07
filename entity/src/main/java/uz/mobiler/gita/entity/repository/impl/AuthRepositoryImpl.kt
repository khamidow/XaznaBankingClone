package uz.mobiler.gita.entity.repository.impl

import android.util.Log
import com.google.gson.Gson
import uz.mobiler.gita.entity.repository.AuthRepository
import uz.mobiler.gita.entity.source.local.TokenManager
import uz.mobiler.gita.entity.source.remote.api.AuthApi
import uz.mobiler.gita.entity.source.remote.request.OtpRequest
import uz.mobiler.gita.entity.source.remote.request.RefreshTokenRequest
import uz.mobiler.gita.entity.source.remote.request.SetPinRequest
import uz.mobiler.gita.entity.source.remote.request.VerifyOtpRequest
import uz.mobiler.gita.entity.source.remote.response.OtpGeneralErrorResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val authApi: AuthApi,
    private val gson: Gson
) : AuthRepository {

    override suspend fun sendOtp(phone: String): Result<String> {
        Log.d("TTT", "rep-phoneOtp")
        val request = OtpRequest(phone)
        Log.d("TTT", "rep-phoneOtp")
        val response = authApi.sendOtp(request)
        Log.d("TTT", "rep-phoneOtp")

        return if (response.isSuccessful && response.body() != null) {
            Log.d("TTT", "success")
            Result.success(response.body()?.data?.message.toString())
        } else {
            Log.d("TTT", "error")
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Log.d("TTT", "$errorMessage")
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }

    override suspend fun verifyOtp(phone: String, otp: String): Result<Boolean> {
        val request = VerifyOtpRequest(phone, otp)
        val response = authApi.verifyOtp(request)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data
            tokenManager.accessToken = data?.accessToken.toString()
            tokenManager.refreshToken = data?.refreshToken.toString()
            Result.success(data?.isNewUser.toBoolean())
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }

//    override suspend fun refreshToken(): Result<Boolean> {
//        val request = RefreshTokenRequest(tokenManager.refreshToken)
//        val response = authApi.refreshToken(request)
//
//        return if (response.isSuccessful && response.body() != null) {
//            val data = response.body()?.data
//            tokenManager.accessToken = data?.accessToken.toString()
//            tokenManager.refreshToken = data?.refreshToken.toString()
//            Result.success(data?.isNewUser.toBoolean())
//        } else {
//            val errorJson = response.errorBody()?.string()
//            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
//            else {
//                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
//                Result.failure(Throwable(errorMessage.error.message))
//            }
//        }
//    }

    override suspend fun setPin(pin: String): Result<String> {
        val request = SetPinRequest(pin)
        val response = authApi.setPin(request)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data
            Result.success(data?.message.toString())
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }

    override suspend fun logout(): Result<String> {
        val request = RefreshTokenRequest(tokenManager.refreshToken)
        val response = authApi.logout(request)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data
            tokenManager.accessToken = ""
            tokenManager.refreshToken = ""
            Result.success(data?.message.toString())
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
