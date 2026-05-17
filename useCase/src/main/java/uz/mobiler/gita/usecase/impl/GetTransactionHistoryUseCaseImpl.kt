package uz.mobiler.gita.usecase.impl

import uz.mobiler.gita.entity.repository.UsersRepository
import uz.mobiler.gita.usecase.GetTransactionHistoryUseCase
import javax.inject.Inject

class GetTransactionHistoryUseCaseImpl @Inject constructor(
    private val repository: UsersRepository
) : GetTransactionHistoryUseCase {
    override operator fun invoke(
        cardId: String,
        type: String
    ) = repository.getTransactions(cardId, type)
}