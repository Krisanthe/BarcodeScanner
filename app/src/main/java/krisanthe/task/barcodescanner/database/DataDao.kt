package krisanthe.task.barcodescanner.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: DataEntity)

    @Query("SELECT * from data order by timestamp DESC limit 2")
    suspend fun getFistTwoItems(): List<DataEntity>

    @Query("SELECT * from data order by timestamp DESC limit 2 OFFSET 8")
    suspend fun getLastTwoItems(): List<DataEntity>

    @Query("SELECT COUNT(timestamp) FROM data")
    suspend fun getRowCount(): Int

    @Query("delete from data where timestamp=(SELECT timestamp from data order by timestamp ASC  limit 1)")
    fun removeOldestEntity()
}