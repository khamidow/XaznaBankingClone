package uz.mobiler.gita.entity.source.remote.response

data class UserResponse (
    val id:String,
    val phone:String,
    val fullName:String,
    val isKycVerified: Boolean,
    val createdAt:String
)