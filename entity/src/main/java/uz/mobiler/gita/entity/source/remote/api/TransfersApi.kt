package uz.mobiler.gita.entity.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import uz.mobiler.gita.entity.source.remote.request.TransferConfirmOtpRequest
import uz.mobiler.gita.entity.source.remote.request.TransferConfirmPinRequest
import uz.mobiler.gita.entity.source.remote.request.TransferRequest
import uz.mobiler.gita.entity.source.remote.response.CardGeneralResponse
import uz.mobiler.gita.entity.source.remote.response.CheckCardResponse
import uz.mobiler.gita.entity.source.remote.response.TransferConfirmPinResponse
import uz.mobiler.gita.entity.source.remote.response.TransferResponse

interface TransfersApi {
    @POST("/api/v1/transfers")
    @Headers("Requires-Auth: true")
    suspend fun initiateTransfer(@Body transferRequest: TransferRequest): Response<CardGeneralResponse<TransferResponse>>

    @POST("/api/v1/transfers/confirm-pin")
    @Headers("Requires-Auth: true")
    suspend fun confirmPin(@Body transferConfirmPinRequest: TransferConfirmPinRequest): Response<CardGeneralResponse<TransferConfirmPinResponse>>

    @POST("/api/v1/transfers/confirm-otp")
    @Headers("Requires-Auth: true")
    suspend fun confirmOtp(@Body transferConfirmOtpRequest: TransferConfirmOtpRequest):  Response<CardGeneralResponse<TransferConfirmPinResponse>>

    @GET("/api/v1/transfers/check-card")
    suspend fun checkCard(@Query("cardNumber") cardNumber: String):  Response<CardGeneralResponse<CheckCardResponse>>
}
