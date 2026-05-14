package uz.mobiler.gita.entity.source.remote.request

data class TransferConfirmOtpRequest(
    val confirmToken:String,
    val otp:String,
)
