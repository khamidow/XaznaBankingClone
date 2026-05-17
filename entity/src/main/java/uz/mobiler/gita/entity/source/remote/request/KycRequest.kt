package uz.mobiler.gita.entity.source.remote.request

data class KycRequest(
    val passportSeries: String,
    val passportNumber: String,
    val birthDate: String,
    val selfieBase64: String
)
