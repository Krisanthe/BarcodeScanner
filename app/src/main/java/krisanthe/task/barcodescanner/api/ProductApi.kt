package krisanthe.task.barcodescanner.api

import krisanthe.task.barcodescanner.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    companion object {
        private const val API_PREFIX = "api/v0"
    }

    @GET("${API_PREFIX}/product/{code}")
    suspend fun productName(@Path("code") code: String): ProductResponse
}