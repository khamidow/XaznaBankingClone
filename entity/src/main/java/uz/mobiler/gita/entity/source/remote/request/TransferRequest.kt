package uz.mobiler.gita.entity.source.remote.request

data class TransferRequest(
    val fromCardId: String,
    val toCardNumber: String,
    val amount: Long,
    val pin: String,
    val description: String
)
