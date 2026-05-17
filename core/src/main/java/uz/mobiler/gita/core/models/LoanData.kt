package uz.mobiler.gita.core.models

data class LoanData(
    val id:String,
    val totalAmount: Double,
    val remaining:Double,
    val monthlyPayment:Double,
    val termMonths:Long,
    val nextDueDate:String,
    val status: String
)
