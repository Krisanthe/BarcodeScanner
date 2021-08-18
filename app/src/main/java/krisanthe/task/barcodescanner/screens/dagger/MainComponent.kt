package krisanthe.task.barcodescanner.screens.dagger

import dagger.Component
import krisanthe.task.barcodescanner.dagger.AppComponent
import krisanthe.task.barcodescanner.screens.MainActivity

@MainScope
@Component(
    modules = [
        MainModule::class,
    ],
    dependencies = [
        AppComponent::class
    ]
)
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}