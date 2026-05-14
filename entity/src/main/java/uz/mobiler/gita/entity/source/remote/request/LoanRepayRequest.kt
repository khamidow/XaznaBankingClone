package uz.mobiler.gita.entity.source.remote.request

data class LoanRepayRequest (
    val cardId:String,
    val amount: Long
)