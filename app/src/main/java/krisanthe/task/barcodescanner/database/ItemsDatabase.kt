package krisanthe.task.barcodescanner.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataEntity::class], version = 1)
abstract class ItemsDatabase : RoomDatabase() {

    abstract fun dataDao(): DataDao
}
