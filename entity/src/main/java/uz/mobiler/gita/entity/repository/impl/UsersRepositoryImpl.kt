package uz.mobiler.gita.entity.repository.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.core.models.UserData
import uz.mobiler.gita.entity.repository.CardsRepository
import uz.mobiler.gita.entity.repository.UsersRepository
import uz.mobiler.gita.entity.source.local.TokenManager
import uz.mobiler.gita.entity.source.remote.api.CardsApi
import uz.mobiler.gita.entity.source.remote.api.UsersApi
import uz.mobiler.gita.entity.source.remote.request.AttachCardRequest
import uz.mobiler.gita.entity.source.remote.request.ProfileNameRequest
import uz.mobiler.gita.entity.source.remote.response.CardDataResponse
import uz.mobiler.gita.entity.source.remote.response.OtpGeneralErrorResponse
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class UsersRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val usersApi: UsersApi,
    private val gson: Gson,
    private val sharedPref: SharedPreferences
) : UsersRepository {

    override suspend fun getUserInfo(): Result<UserData> {
        val response = usersApi.getUserInfo()

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data!!
            sharedPref.edit {
                putString("user_id", data.id)
                putString("name", data.fullName)
                putBoolean("is_kyc_verified", data.isKycVerified)
                putString("created_at", data.createdAt)
            }
            Result.success(
                UserData(
                    data.id,
                    data.phone,
                    data.fullName,
                    data.isKycVerified,
                    data.createdAt
                )
            )
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson == null) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, OtpGeneralErrorResponse::class.java)
                Result.failure(Throwable(errorMessage.error.message))
            }
        }
    }

    override suspend fun updateUserName(name: String): Result<Boolean> {
        val request = ProfileNameRequest(name)
        val response = usersApi.updateProfileName(request)

        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()?.data!!
            sharedPref.edit {
                putString("user_id", data.id)
                putString("name", data.fullName)
                putBoolean("is_kyc_verified", data.isKycVerified)
                putString("created_at", data.createdAt)
            }
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
