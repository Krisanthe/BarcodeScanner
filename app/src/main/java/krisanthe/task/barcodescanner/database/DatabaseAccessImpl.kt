package krisanthe.task.barcodescanner.database

import krisanthe.task.barcodescanner.common.safeApiCall
import krisanthe.task.barcodescanner.common.toEntity
import krisanthe.task.barcodescanner.common.toItems
import krisanthe.task.barcodescanner.model.Item

class DatabaseAccessImpl(
    private val database: ItemsDatabase
) : DatabaseAccess {

    override suspend fun getFistTwoItems(): List<Item>? =
        safeApiCall {
            database.dataDao().getFistTwoItems().toItems()
        }

    override suspend fun getLastTwoItems(): List<Item>? =
        safeApiCall {
            database.dataDao().getLastTwoItems().toItems()
        }

    override suspend fun saveRecord(item: Item) {
        safeApiCall {
            if (database.dataDao().getRowCount() == 10) {
                database.dataDao().removeOldestEntity()
            }
            database.dataDao().insertData(item.toEntity())
        }
    }
}