package uz.mobiler.gita.entity.repository

interface AuthRepository {
    suspend fun sendOtp(phone:String): Result<String>
    suspend fun verifyOtp(phone:String,otp:String): Result<Boolean>
    suspend fun refreshToken(): Result<Boolean>
    suspend fun setPin(pin:String): Result<String>
    suspend fun logout(): Result<String>
}