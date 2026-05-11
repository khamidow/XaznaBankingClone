package uz.mobiler.gita.core.models

data class UserData(
    val id:String,
    val phone:String,
    val fullName:String,
    val isKycVerified: Boolean,
    val createdAt:String
)
