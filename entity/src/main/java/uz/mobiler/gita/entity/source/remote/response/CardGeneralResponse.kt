package uz.mobiler.gita.entity.source.remote.response

data class CardGeneralResponse<T> (
    val success: Boolean,
    val data: T
)