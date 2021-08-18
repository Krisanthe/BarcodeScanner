package krisanthe.task.barcodescanner.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("product") val product: ProductInfo,
)
