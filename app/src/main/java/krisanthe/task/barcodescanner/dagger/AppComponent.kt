package krisanthe.task.barcodescanner.dagger

import dagger.Component
import krisanthe.task.barcodescanner.api.ProductTransaction
import krisanthe.task.barcodescanner.database.DatabaseAccess
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
    ]
)
interface AppComponent {

    val databaseAccess: DatabaseAccess
    val productTransaction: ProductTransaction
}