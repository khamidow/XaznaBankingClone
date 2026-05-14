package uz.mobiler.gita.xaznabankingclone.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class WalletNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(10)

        val formatted = buildString {
            trimmed.forEachIndexed { index, c ->
                append(c)

                if (index == 4 && index != trimmed.lastIndex) {
                    append(" ")
                }
            }
        }

        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 5 -> offset
                    offset <= 10 -> offset + 1
                    else -> formatted.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 5 -> offset
                    offset <= 11 -> offset - 1
                    else -> trimmed.length
                }
            }
        }

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = offsetMapping
        )
    }
}