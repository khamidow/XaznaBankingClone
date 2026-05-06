package uz.mobiler.gita.entity.source.remote.response

data class OtpGeneralErrorResponse(
    val success: Boolean,
    val error: ErrorMessageResponse
)
