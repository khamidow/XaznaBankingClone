package uz.mobiler.gita.entity.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import uz.mobiler.gita.entity.source.remote.request.MakePaymentRequest
import uz.mobiler.gita.entity.source.remote.response.CardGeneralResponse
import uz.mobiler.gita.entity.source.remote.response.JustMessageResponse
import uz.mobiler.gita.entity.source.remote.response.PaymentResponse

interface PaymentsApi {
    @GET("/api/v1/payments/providers")
    @Headers("Requires-Auth: true")
    suspend fun getPaymentProviders(@Query("category") category: String): Response<CardGeneralResponse<List<PaymentResponse>>>

    @POST("/api/v1/payments")
    @Headers("Requires-Auth: true")
    suspend fun makePayment(@Body makePaymentRequest: MakePaymentRequest): Response<CardGeneralResponse<JustMessageResponse>>
}
