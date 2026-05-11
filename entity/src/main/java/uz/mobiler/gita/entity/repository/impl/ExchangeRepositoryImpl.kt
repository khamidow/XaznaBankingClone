package uz.mobiler.gita.entity.repository.impl

import com.google.gson.Gson
import uz.mobiler.gita.core.models.ExchangeData
import uz.mobiler.gita.entity.repository.ExchangeRepository
import uz.mobiler.gita.entity.source.local.TokenManager
import uz.mobiler.gita.entity.source.remote.api.ExchangeApi
import uz.mobiler.gita.entity.source.remote.response.OtpGeneralErrorResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val exchangeApi: ExchangeApi,
    private val gson: Gson
) : ExchangeRepository {
    override suspend fun getExchangeRates(): Result<List<ExchangeData>> {
        val response = exchangeApi.getExchangeRates()

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data?.map { ExchangeData(it.currency,it.buy,it.sell,it.updatedAt) }
            Result.success(data!!)
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