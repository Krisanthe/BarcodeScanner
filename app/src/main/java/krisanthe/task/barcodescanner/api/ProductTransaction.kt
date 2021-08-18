package krisanthe.task.barcodescanner.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductTransaction @Inject constructor(
    private val productApi: ProductApi,
) {

    suspend fun getProductName(code: String): String? {
        return safeApiCall {
            productApi.productName(code).product.name
        }
    }
}

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
