package uz.mobiler.gita.entity.source.remote.response

data class TransactionGeneralResponse<T>(
    val success: Boolean,
    val data: T,
    val meta: TransactionMetaResponse
)
