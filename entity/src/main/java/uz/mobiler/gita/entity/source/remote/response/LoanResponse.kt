package uz.mobiler.gita.entity.source.remote.response

data class LoanResponse (
    val id:String,
    val totalAmount: Double,
    val remaining:Double,
    val monthlyPayment:Double,
    val termMonths:Long,
    val nextDueDate:String,
    val status: String
)