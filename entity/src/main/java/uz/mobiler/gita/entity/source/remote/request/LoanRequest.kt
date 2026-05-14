package uz.mobiler.gita.entity.source.remote.request

data class LoanRequest (
    val amount:Long,
    val termMonths:Long,
    val cardId:String
)