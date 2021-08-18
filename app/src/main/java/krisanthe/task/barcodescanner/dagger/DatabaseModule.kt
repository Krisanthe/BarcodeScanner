package krisanthe.task.barcodescanner.dagger

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import krisanthe.task.barcodescanner.database.DatabaseAccess
import krisanthe.task.barcodescanner.database.DatabaseAccessImpl
import krisanthe.task.barcodescanner.database.ItemsDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    companion object {
        private const val DATABASE_NAME = "barcodeItems.db"
    }

    @Singleton
    @Provides
    fun provideDatabase(
        application: Application,
    ): ItemsDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            ItemsDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideDatabaseAccess(
        database: ItemsDatabase,
    ): DatabaseAccess = DatabaseAccessImpl(database)

}