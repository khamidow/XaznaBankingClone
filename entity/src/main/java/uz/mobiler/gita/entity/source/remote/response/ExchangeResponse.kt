package uz.mobiler.gita.entity.source.remote.response


data class ExchangeResponse(
    val currency: String,
    val buy: Double,
    val sell : Double,
    val updatedAt:String
)
