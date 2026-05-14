package uz.mobiler.gita.entity.source.remote.request

data class MakePaymentRequest (
    val providerId:String,
    val cardId:String,
    val amount:Long,
    val account: String
)