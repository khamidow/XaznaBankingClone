package uz.mobiler.gita.entity.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import uz.mobiler.gita.entity.source.remote.request.ProfileNameRequest
import uz.mobiler.gita.entity.source.remote.response.CardGeneralResponse
import uz.mobiler.gita.entity.source.remote.response.UserResponse

interface UsersApi {
    @GET("/api/v1/users/me")
    @Headers("Requires-Auth: true")
    suspend fun getUserInfo(): Response<CardGeneralResponse<UserResponse>>

    @PATCH("/api/v1/users/me")
    @Headers("Requires-Auth: true")
    suspend fun updateProfileName(@Body profileNameRequest: ProfileNameRequest): Response<CardGeneralResponse<UserResponse>>
}