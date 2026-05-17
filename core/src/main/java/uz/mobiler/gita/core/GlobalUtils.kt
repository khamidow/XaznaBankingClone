package uz.mobiler.gita.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import uz.mobiler.gita.core.models.TransactionData
import uz.mobiler.gita.core.models.TransactionUiItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun TransactionData.dateKey(): String {
    val dateTime = LocalDateTime.parse(createdAt)

    return dateTime.format(
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun PagingData<TransactionData>.toUiModel(): PagingData<TransactionUiItem> {

    return this
        .map<TransactionData, TransactionUiItem> {
            TransactionUiItem.Item(it)
        }
        .insertSeparators { before, after ->
            if (after == null) return@insertSeparators null

            val beforeDate = (before as? TransactionUiItem.Item)
                ?.item
                ?.dateKey()

            val afterDate = (after as TransactionUiItem.Item)
                .item
                .dateKey()

            if (beforeDate != afterDate) {
                TransactionUiItem.Header(afterDate)
            } else {
                null
            }
        }
}