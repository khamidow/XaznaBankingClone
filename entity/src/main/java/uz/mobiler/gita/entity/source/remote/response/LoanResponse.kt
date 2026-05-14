package uz.mobiler.gita.entity.source.remote.response

data class LoanResponse (
    val id:String,
    val totalAmount: Long,
    val remaining:Long,
    val monthlyPayment:Long,
    val termMonths:Long,
    val nextDueDate:String,
    val status: String
)