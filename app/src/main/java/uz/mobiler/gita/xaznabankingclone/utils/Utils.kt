package uz.mobiler.gita.xaznabankingclone.utils

import uz.mobiler.gita.xaznabankingclone.R

fun String.toIconBank():Int{
    return when(this){
        "Xalq Banki" -> R.drawable.ic_xalqbanki_logo
        "Aloqa Bank" -> R.drawable.ic_aloqabank_logo
        else -> R.drawable.ic_xalqbanki_logo
    }
}

fun String.toIconCard():Int{
    return when(this){
        "UZCARD" -> R.drawable.ic_uzcard_logo
        else -> R.drawable.ic_uzcard_logo
    }
}

fun Long.formatUzs(): String {
    val formatter = java.text.DecimalFormat("#,##0.00")
    val symbols = java.text.DecimalFormatSymbols().apply {
        groupingSeparator = ' '
        decimalSeparator = '.'
    }
    formatter.decimalFormatSymbols = symbols

    return "${formatter.format(this)} UZS"
}