package uz.mobiler.gita.entity.repository

import uz.mobiler.gita.core.models.ExchangeData
import uz.mobiler.gita.entity.source.remote.response.ExchangeResponse

interface ExchangeRepository {
    suspend fun getExchangeRates(): Result<List<ExchangeData>>
}