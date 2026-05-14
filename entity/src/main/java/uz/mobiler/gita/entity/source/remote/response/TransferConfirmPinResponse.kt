package uz.mobiler.gita.entity.source.remote.response

data class TransferConfirmPinResponse (
    val transactionId: String,
    val status: String,
    val amount:Long
)