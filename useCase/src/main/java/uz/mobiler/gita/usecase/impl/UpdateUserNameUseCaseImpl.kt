package uz.mobiler.gita.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.core.models.UserData
import uz.mobiler.gita.entity.repository.CardsRepository
import uz.mobiler.gita.entity.repository.UsersRepository
import uz.mobiler.gita.entity.source.remote.response.CardDataResponse
import uz.mobiler.gita.usecase.GetCardsUseCase
import uz.mobiler.gita.usecase.GetUserInfoUseCase
import uz.mobiler.gita.usecase.UpdateUserNameUseCase
import javax.inject.Inject

class UpdateUserNameUseCaseImpl @Inject constructor(private val rep: UsersRepository) : UpdateUserNameUseCase {
    override fun invoke(name: String): Flow<Result<Boolean>> = flow {
        emit(rep.updateUserName(name))
    }.catch {
        emit(Result.failure(Throwable(it.message.toString())))
    }.flowOn(Dispatchers.IO)
}