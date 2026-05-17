package uz.mobiler.gita.entity.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.Query
import uz.mobiler.gita.entity.source.remote.request.ProfileNameRequest
import uz.mobiler.gita.entity.source.remote.response.CardGeneralResponse
import uz.mobiler.gita.entity.source.remote.response.TransactionGeneralResponse
import uz.mobiler.gita.entity.source.remote.response.TransactionResponse
import uz.mobiler.gita.entity.source.remote.response.UserResponse

interface UsersApi {
    @GET("/api/v1/users/me")
    @Headers("Requires-Auth: true")
    suspend fun getUserInfo(): Response<CardGeneralResponse<UserResponse>>

    @PATCH("/api/v1/users/me")
    @Headers("Requires-Auth: true")
    suspend fun updateProfileName(@Body profileNameRequest: ProfileNameRequest): Response<CardGeneralResponse<UserResponse>>

    @GET("/api/v1/users/transactions")
    @Headers("Requires-Auth: true")
    suspend fun getTransactionHistory(
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int = 20,
        @Query("cardId") cardId: String,
        @Query("type") type: String = ""
    ): Response<TransactionGeneralResponse<List<TransactionResponse>>>
}