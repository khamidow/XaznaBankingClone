package uz.mobiler.gita.entity.repository

import uz.mobiler.gita.core.models.CardData

interface TransfersRepository {
    suspend fun initiateTransfer(cardId:String, toCardNumber:String, amount:Long,description:String): Result<Boolean>
    suspend fun confirmPin(pin:String): Result<Boolean>
    suspend fun confirmOtp(otp:String): Result<Boolean>
    suspend fun checkCard(cardNumber: String): Result<String>
}