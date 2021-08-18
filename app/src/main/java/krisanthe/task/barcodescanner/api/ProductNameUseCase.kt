package krisanthe.task.barcodescanner.api

import krisanthe.task.barcodescanner.common.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductNameUseCase @Inject constructor(
    private val productApi: ProductApi,
) {

    suspend fun getProductName(code: String): String? {
        return safeApiCall {
            productApi.productName(code).product.name
        }
    }
}
