package krisanthe.task.barcodescanner.common

class AdapterData<out T>(
    val viewType: Int,
    val data: T
)