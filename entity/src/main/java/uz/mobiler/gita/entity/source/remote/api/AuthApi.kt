package uz.mobiler.gita.entity.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import uz.mobiler.gita.entity.source.remote.request.OtpRequest
import uz.mobiler.gita.entity.source.remote.request.RefreshTokenRequest
import uz.mobiler.gita.entity.source.remote.request.SetPinRequest
import uz.mobiler.gita.entity.source.remote.request.VerifyOtpRequest
import uz.mobiler.gita.entity.source.remote.response.JustMessageResponse
import uz.mobiler.gita.entity.source.remote.response.OtpGeneralSuccessResponse
import uz.mobiler.gita.entity.source.remote.response.VerifyOtpResponse

interface AuthApi {
    @POST("/api/v1/auth/send-otp")
    suspend fun sendOtp(@Body otpRequest: OtpRequest): Response<OtpGeneralSuccessResponse<JustMessageResponse>>

    @POST("/api/v1/auth/verify-otp")
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Response<OtpGeneralSuccessResponse<VerifyOtpResponse>>

    @POST("/api/v1/auth/refresh")
    suspend fun refreshToken(@Body refreshToken: RefreshTokenRequest): Response<OtpGeneralSuccessResponse<VerifyOtpResponse>>

    @POST("/api/v1/auth/set-pin")
    @Headers("Requires-Auth: true")
    suspend fun setPin(@Body setPinRequest: SetPinRequest): Response<OtpGeneralSuccessResponse<JustMessageResponse>>

    @POST("/api/v1/auth/logout")
    suspend fun logout(@Body refreshToken: RefreshTokenRequest): Response<OtpGeneralSuccessResponse<JustMessageResponse>>
}
