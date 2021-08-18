package krisanthe.task.barcodescanner.dagger

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import krisanthe.task.barcodescanner.api.ProductApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val USER_AGENT_KEY = "User-Agent"
        private const val USER_AGENT = "BarcodeScanner - Android - Version 1.0"
    }

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String = "https://world.openfoodfacts.org/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder().header(USER_AGENT_KEY, USER_AGENT).build()
                )
            }
            .connectTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideProductApi(
        @Named("baseUrl") baseUrl: String,
        okHttpClient: OkHttpClient,
    ): ProductApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ProductApi::class.java)
    }
}