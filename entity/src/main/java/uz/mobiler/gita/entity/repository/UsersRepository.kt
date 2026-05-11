package uz.mobiler.gita.entity.repository

import uz.mobiler.gita.core.models.UserData

interface UsersRepository {
    suspend fun getUserInfo(): Result<UserData>
    suspend fun updateUserName(name: String): Result<Boolean>
}