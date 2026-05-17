package uz.mobiler.gita.usecase

import kotlinx.coroutines.flow.Flow
import uz.mobiler.gita.core.models.PaymentData

interface GetPaymentProvidersUseCase {
    suspend operator fun invoke(
        category: String
    ): Flow<Result<List<PaymentData>>>
}