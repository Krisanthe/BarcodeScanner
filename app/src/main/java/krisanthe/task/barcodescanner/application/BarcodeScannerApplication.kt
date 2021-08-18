package krisanthe.task.barcodescanner.application

import android.app.Application
import krisanthe.task.barcodescanner.dagger.AppComponent
import krisanthe.task.barcodescanner.dagger.AppModule
import krisanthe.task.barcodescanner.dagger.DaggerAppComponent
import krisanthe.task.barcodescanner.dagger.DatabaseModule

class BarcodeScannerApplication : Application() {

    companion object {
        var appComponent: AppComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .databaseModule(DatabaseModule())
            .build()
    }
}