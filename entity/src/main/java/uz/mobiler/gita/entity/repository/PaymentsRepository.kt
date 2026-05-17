package uz.mobiler.gita.entity.repository

import uz.mobiler.gita.core.models.PaymentData

interface PaymentsRepository {
    suspend fun getPaymentProviders(category:String): Result<List<PaymentData>>
    suspend fun makePayment(providerId:String, cardId:String,amount:Long,account: String): Result<Boolean>
}