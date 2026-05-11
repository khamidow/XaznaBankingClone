package uz.mobiler.gita.entity.repository

import uz.mobiler.gita.core.models.ExchangeData

interface ExchangeRepository {
    suspend fun getExchangeRates(): Result<List<ExchangeData>>
}