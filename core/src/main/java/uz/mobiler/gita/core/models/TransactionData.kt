package uz.mobiler.gita.core.models

data class TransactionData(
    val id:String,
    val type:String,
    val amount: Long,
    val currency:String,
    val description:String,
    val status:String,
    val cardId:String,
    val createdAt:String
)
