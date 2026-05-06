package uz.mobiler.gita.entity.source.remote.response

data class OtpGeneralSuccessResponse<T>(
    val success: Boolean,
    val data: T
)
