package uz.mobiler.gita.entity.repository

import uz.mobiler.gita.core.models.CardData
import uz.mobiler.gita.entity.source.remote.response.CardDataResponse

interface CardsRepository {
    suspend fun getCards(): Result<List<CardData>>
    suspend fun attachCard(number: String): Result<String>
    suspend fun detachCard(id: String): Result<Boolean>
    suspend fun blockCard(id: String): Result<Boolean>
}