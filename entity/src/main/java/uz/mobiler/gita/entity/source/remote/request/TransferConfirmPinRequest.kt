package uz.mobiler.gita.entity.source.remote.request

data class TransferConfirmPinRequest (
    val confirmToken:String,
    val pin:String
)