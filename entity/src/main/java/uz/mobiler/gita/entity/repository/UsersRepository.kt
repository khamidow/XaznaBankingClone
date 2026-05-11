package uz.mobiler.gita.entity.repository

import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.core.models.UserData
import uz.mobiler.gita.entity.source.remote.response.CardDataResponse

interface UsersRepository {
    suspend fun getUserInfo(): Result<UserData>
    suspend fun updateUserName(name: String): Result<Boolean>
}