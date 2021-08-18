package krisanthe.task.barcodescanner.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import krisanthe.task.barcodescanner.api.ProductTransaction
import krisanthe.task.barcodescanner.database.DatabaseAccess
import krisanthe.task.barcodescanner.model.Item

class MainViewModel(
    private val databaseAccess: DatabaseAccess,
    private val productTransaction: ProductTransaction,
) : ViewModel() {

    fun saveResult(item: Item) {
        viewModelScope.launch {
            val updatedItem = item.copy(name = productTransaction.getProductName(item.code))
            databaseAccess.saveRecord(updatedItem)
        }
    }

    suspend fun getFirstTwoRecords(): List<Item> =
        databaseAccess.getFistTwoItems() ?: emptyList()

    suspend fun getLastTwoRecords(): List<Item> =
        databaseAccess.getLastTwoItems() ?: emptyList()
}