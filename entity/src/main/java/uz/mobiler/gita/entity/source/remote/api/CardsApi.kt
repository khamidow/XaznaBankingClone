package uz.mobiler.gita.entity.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import uz.mobiler.gita.entity.source.remote.request.AttachCardRequest
import uz.mobiler.gita.entity.source.remote.response.CardDataResponse
import uz.mobiler.gita.entity.source.remote.response.CardGeneralResponse
import uz.mobiler.gita.entity.source.remote.response.JustMessageResponse
import uz.mobiler.gita.entity.source.remote.response.OtpGeneralSuccessResponse

interface CardsApi {
    @GET("/api/v1/cards")
    @Headers("Requires-Auth: true")
    suspend fun attachedCards(): Response<CardGeneralResponse<List<CardDataResponse>>>

    @POST("/api/v1/cards/attach")
    @Headers("Requires-Auth: true")
    suspend fun attachCardByNumber(@Body attachCardRequest: AttachCardRequest): Response<CardGeneralResponse<CardDataResponse>>

    @POST("/api/v1/cards/{id}")
    @Headers("Requires-Auth: true")
    suspend fun detachCardById(@Path("id") id: String):  Response<OtpGeneralSuccessResponse<JustMessageResponse>>

    @POST("/api/v1/cards/{id}/block")
    @Headers("Requires-Auth: true")
    suspend fun blockCardById(@Path("id") id: String):  Response<OtpGeneralSuccessResponse<JustMessageResponse>>

    @POST("/api/v1/cards/{id}/main")
    @Headers("Requires-Auth: true")
    suspend fun setMainCardById(@Path("id") id: String):  Response<OtpGeneralSuccessResponse<JustMessageResponse>>
}
