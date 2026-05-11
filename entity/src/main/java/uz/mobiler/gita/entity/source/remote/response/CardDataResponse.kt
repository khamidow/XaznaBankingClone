package uz.mobiler.gita.entity.source.remote.response

data class CardDataResponse (
    val id:String,
    val maskedNumber :String,
    val holderName:String,
    val expiry:String,
    val balance:Long,
    val currency:String,
    val isMain: Boolean,
    val isBlocked: Boolean,
    val type:String
)