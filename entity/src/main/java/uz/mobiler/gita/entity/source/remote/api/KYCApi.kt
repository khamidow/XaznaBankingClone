package uz.mobiler.gita.entity.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import uz.mobiler.gita.entity.source.remote.request.KycRequest
import uz.mobiler.gita.entity.source.remote.response.CardGeneralResponse
import uz.mobiler.gita.entity.source.remote.response.JustMessageResponse
import uz.mobiler.gita.entity.source.remote.response.KycStatusResponse

interface KYCApi {
    @GET("/api/v1/kyc")
    @Headers("Requires-Auth: true")
    suspend fun getKycStatus(): Response<CardGeneralResponse<KycStatusResponse>>

    @POST("/api/v1/kyc")
    @Headers("Requires-Auth: true")
    suspend fun submitKycDocuments(@Body kycRequest: KycRequest): Response<CardGeneralResponse<JustMessageResponse>>

}