package uz.mobiler.gita.entity.source.remote.request

data class VerifyOtpRequest(
    val phone:String,
    val otp: String
)
