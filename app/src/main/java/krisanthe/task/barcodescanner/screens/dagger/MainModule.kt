package krisanthe.task.barcodescanner.screens.dagger

import dagger.Module
import dagger.Provides
import krisanthe.task.barcodescanner.api.ProductNameUseCase
import krisanthe.task.barcodescanner.common.viewModel
import krisanthe.task.barcodescanner.database.DatabaseAccess
import krisanthe.task.barcodescanner.screens.MainActivity
import krisanthe.task.barcodescanner.screens.MainViewModel

@Module
class MainModule(
    private val activity: MainActivity
) {

    @Provides
    @MainScope
    fun provideViewModel(
        databaseAccess: DatabaseAccess,
        productNameUseCase: ProductNameUseCase,
    ): MainViewModel = activity.viewModel {
        MainViewModel(databaseAccess, productNameUseCase)
    }
}