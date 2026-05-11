package uz.mobiler.gita.entity.source.remote.api

import retrofit2.Response
import retrofit2.http.GET
import uz.mobiler.gita.entity.source.remote.response.CardGeneralResponse
import uz.mobiler.gita.entity.source.remote.response.ExchangeResponse

interface ExchangeApi {
    @GET("/api/v1/exchange/rates")
    suspend fun getExchangeRates(): Response<CardGeneralResponse<List<ExchangeResponse>>>
}