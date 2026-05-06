package uz.mobiler.gita.entity.source.remote.response

data class VerifyOtpResponse (
    val accessToken:String,
    val refreshToken:String,
    val isNewUser: String
)