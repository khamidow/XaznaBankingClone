package uz.mobiler.gita.core.models

data class ExchangeData (
    val currency: String,
    val buy: Double,
    val sell : Double,
    val updatedAt:String
)