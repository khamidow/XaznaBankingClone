package uz.mobiler.gita.entity.source.remote.response

data class TransferResponse(
    val requiresConfirmation: Boolean,
    val confirmToken:String,
    val status:String
)
