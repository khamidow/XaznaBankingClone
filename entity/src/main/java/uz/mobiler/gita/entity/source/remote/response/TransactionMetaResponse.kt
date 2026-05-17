package uz.mobiler.gita.entity.source.remote.response

data class TransactionMetaResponse (
    val page:Int,
    val pageSize:Int,
    val total:Int,
    val totalPages:Int
)