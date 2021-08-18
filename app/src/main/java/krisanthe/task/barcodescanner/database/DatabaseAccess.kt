package krisanthe.task.barcodescanner.database

import krisanthe.task.barcodescanner.model.Item

interface DatabaseAccess {

    suspend fun getFistTwoItems(): List<Item>?

    suspend fun getLastTwoItems(): List<Item>?

    suspend fun saveRecord(item: Item)
}