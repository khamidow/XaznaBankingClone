package uz.mobiler.gita.entity.source.remote.response

data class TransactionResponse (
    val id:String,
    val type:String,
    val amount: Long,
    val currency:String,
    val description:String,
    val status:String,
    val cardId:String,
    val createdAt:String
)