package uz.mobiler.gita.entity.repository.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.entity.repository.CardsRepository
import uz.mobiler.gita.entity.source.local.TokenManager
import uz.mobiler.gita.entity.source.remote.api.CardsApi
import uz.mobiler.gita.entity.source.remote.request.AttachCardRequest
import uz.mobiler.gita.entity.source.remote.response.OtpGeneralErrorResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardsRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val cardsApi: CardsApi,
    private val gson: Gson,
    private val pref: SharedPreferences
) : CardsRepository {
    override suspend fun getCards(): Result<List<CardData>> {
        val response = cardsApi.attachedCards()

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data?.map { CardData(it.id,it.maskedNumber,it.holderName,it.expiry,it.balance,it.currency,it.isMain,it.isBlocked,it.type) }
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

    override suspend fun attachCard(number: String,bcg:String): Result<String> {
        val request = AttachCardRequest(number)
        val response = cardsApi.attachCardByNumber(request)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data
            pref.edit().putString("card_${data?.id.toString()}",bcg).apply()
            pref.edit().putString(data?.id.toString(),number).apply()
            Result.success("Success")
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }

    override suspend fun detachCard(id: String): Result<Boolean> {
        val response = cardsApi.detachCardById(id)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data
            pref.edit().putString("card_$id","").apply()
            Result.success(true)
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }

    override suspend fun blockCard(id: String): Result<Boolean> {
        val response = cardsApi.blockCardById(id)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data
            Result.success(true)
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }

    override suspend fun setMainCard(id: String): Result<Boolean> {
        val response = cardsApi.setMainCardById(id)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data
            Result.success(true)
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