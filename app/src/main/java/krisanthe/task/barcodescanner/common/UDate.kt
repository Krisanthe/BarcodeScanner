package krisanthe.task.barcodescanner.common

import java.text.SimpleDateFormat
import java.util.*

object UDate {
    private val simpleDateFormat = SimpleDateFormat("dd-MM HH:mm:ss", Locale.getDefault())

    fun getDate(timestamp: Long): String = simpleDateFormat.format(Date(timestamp))
}