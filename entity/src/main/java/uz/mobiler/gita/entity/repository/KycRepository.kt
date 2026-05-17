package uz.mobiler.gita.entity.repository

interface KycRepository {
    suspend fun getKycStatus(): Result<Boolean>
    suspend fun submitKycDocuments(
        passportSeries: String,
        passportNumber: String,
        birthDate: String,
        selfieBase64: String
    ): Result<Boolean>
}