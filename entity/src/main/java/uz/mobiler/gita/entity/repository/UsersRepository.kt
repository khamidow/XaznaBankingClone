package uz.mobiler.gita.entity.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.mobiler.gita.core.models.TransactionData
import uz.mobiler.gita.core.models.UserData

interface UsersRepository {
    suspend fun getUserInfo(): Result<UserData>
    suspend fun updateUserName(name: String): Result<Boolean>
    fun getTransactions(cardId: String, type: String): Flow<PagingData<TransactionData>>
}