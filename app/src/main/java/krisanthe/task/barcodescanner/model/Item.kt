package krisanthe.task.barcodescanner.model

data class Item(
    val code: String,
    val timestamp: Long,
    val name: String? = null,
)
