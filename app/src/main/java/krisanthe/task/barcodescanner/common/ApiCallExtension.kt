package krisanthe.task.barcodescanner.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): T? =
    withContext(dispatcher) {
        try {
            apiCall()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }