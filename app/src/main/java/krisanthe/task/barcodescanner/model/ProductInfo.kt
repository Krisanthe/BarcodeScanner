package krisanthe.task.barcodescanner.model

import com.google.gson.annotations.SerializedName

data class ProductInfo(
    @SerializedName("product_name") val name: String,
)