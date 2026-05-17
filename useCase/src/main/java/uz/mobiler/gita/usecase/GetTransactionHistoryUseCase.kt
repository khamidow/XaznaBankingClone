package uz.mobiler.gita.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.mobiler.gita.core.models.TransactionData

interface GetTransactionHistoryUseCase {
    operator fun invoke(
        cardId: String,
        type: String
    ): Flow<PagingData<TransactionData>>
}