package uz.mobiler.gita.xaznabankingclone.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import uz.mobiler.gita.xaznabankingclone.R
import java.io.ByteArrayOutputStream
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun String.toIconBank(): Int {
    return when (this) {
        "Xalq Banki" -> R.drawable.ic_xalqbanki_logo
        "Aloqa Bank" -> R.drawable.ic_aloqabank_logo
        else -> R.drawable.ic_xalqbanki_logo
    }
}

fun String.toIconCard(): Int {
    return when (this) {
        "UZCARD" -> R.drawable.ic_uzcard_logo
        "MASTERCARD" -> R.drawable.ic_mastercard_logo
        else -> R.drawable.ic_uzcard_logo
    }
}

fun String.toImagePayment(): Int {
    return when (this) {
        "703f0c66-a588-437c-889b-e0b64a1d3e9d" -> R.drawable.ic_ucell_logo
        "88e5c809-bccc-43db-8ca5-0d14233c5223" -> R.drawable.ic_beeline_logo
        "4d957ff9-fda1-47f9-b115-a712f238580a" -> R.drawable.ic_ums_logo
        "ec2ce324-eb92-4a51-a453-623b59a6a603" -> R.drawable.ic_uzmobile_logo
        "82252280-f9c2-4ce6-84b2-d2dc5c5df1b5" -> R.drawable.ic_perfectum_logo
        "67346efe-2a96-4df5-aa55-da76f6ad2508" -> R.drawable.ic_uztelecom_logo
        "5270a41e-eaf7-48ae-bf02-53a8052c248e" -> R.drawable.ic_sarkortelecom_logo
        "b4e1ac18-0662-4526-bb30-10eb0bc2f9bc" -> R.drawable.ic_gas
        "9e3db573-0bdd-46a8-aa7a-81b54a0d867e" -> R.drawable.ic_energy
        "7f2b300b-1a4d-40a0-a8c4-21eb75c3a4b1" -> R.drawable.ic_utilities
        "ce8acf9f-7036-43b4-9c12-4972ca998814" -> R.drawable.ic_taxreturn
        "ef50e360-4c9c-4634-a3d1-ebac59dbcdb3" -> R.drawable.ic_governmentduties
        else -> R.drawable.ic_image_not_found
    }
}

fun String.toImageCategory(): Int {
    return when (this) {
        "ALL" -> R.drawable.ic_home
        "MOBILE" -> R.drawable.ic_device
        "INTERNET" -> R.drawable.ic_language
        "UTILITY" -> R.drawable.ic_utilities
        "TV" -> R.drawable.ic_tv
        "EDUCATION" -> R.drawable.ic_education
        "OTHER" -> R.drawable.ic_three_dots
        else -> R.drawable.ic_image_not_found
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

@RequiresApi(Build.VERSION_CODES.O)
fun String.toFormattedDate(): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val date = java.time.LocalDate.parse(this, inputFormatter)
        date.format(outputFormatter)
    } catch (e: Exception) {
        this
    }
}

fun formatUzPhone(input: String): String {
    val digits = input.take(9)

    val builder = StringBuilder("+998 ")

    digits.forEachIndexed { index, c ->
        builder.append(c)
        when (index) {
            1 -> builder.append(" ")
            4 -> builder.append(" ")
            6 -> builder.append(" ")
        }
    }

    return builder.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: String): String {
    val parsed = OffsetDateTime.parse(date)

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")

    return parsed.format(formatter)
}

fun String.needPhoneInput(): Boolean {
    return when (this) {
        "703f0c66-a588-437c-889b-e0b64a1d3e9d" -> true
        "88e5c809-bccc-43db-8ca5-0d14233c5223" -> true
        "4d957ff9-fda1-47f9-b115-a712f238580a" -> true
        "ec2ce324-eb92-4a51-a453-623b59a6a603" -> true
        "82252280-f9c2-4ce6-84b2-d2dc5c5df1b5" -> true
        "b4e1ac18-0662-4526-bb30-10eb0bc2f9bc" -> false
        "9e3db573-0bdd-46a8-aa7a-81b54a0d867e" -> false
        "7f2b300b-1a4d-40a0-a8c4-21eb75c3a4b1" -> false
        "67346efe-2a96-4df5-aa55-da76f6ad2508" -> false
        "5270a41e-eaf7-48ae-bf02-53a8052c248e" -> false
        "ce8acf9f-7036-43b4-9c12-4972ca998814" -> false
        "ef50e360-4c9c-4634-a3d1-ebac59dbcdb3" -> false
        else -> false
    }
}

fun formatPassportSeries(input: String): String {
    val letters = input
        .filter { it.isLetter() }
        .uppercase()
        .take(2)

    val digits = input
        .filter { it.isDigit() }
        .take(7)

    return letters + digits
}

fun formatBirthDate(input: String): String {
    val digits = input.filter { it.isDigit() }.take(8)

    return buildString {
        digits.forEachIndexed { index, c ->
            append(c)

            if ((index == 1 || index == 3) && index != digits.lastIndex) {
                append(".")
            }
        }
    }
}

fun imageToBase64(filePath: String): String {
    val bitmap = BitmapFactory.decodeFile(filePath)

    val resized = Bitmap.createScaledBitmap(
        bitmap,
        480,
        640,
        true
    )

    val stream = ByteArrayOutputStream()

    resized.compress(Bitmap.CompressFormat.JPEG, 40, stream)

    val bytes = stream.toByteArray()

    return android.util.Base64.encodeToString(
        bytes,
        android.util.Base64.NO_WRAP
    )
}

fun String.toApiDateFormat(): String {
    val parts = this.split(".")
    if (parts.size != 3) return this
    val (day, month, year) = parts
    return "$year-$month-$day"
}