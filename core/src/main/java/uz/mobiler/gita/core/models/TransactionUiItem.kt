package uz.mobiler.gita.core.models

sealed class TransactionUiItem {
    data class Header(val date: String) : TransactionUiItem()
    data class Item(val item: TransactionData) : TransactionUiItem()
}