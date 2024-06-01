package ru.faimizufarov.simbirtraining.java.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import ru.faimizufarov.simbirtraining.BuildConfig

private const val BASE_URL = BuildConfig.BASE_URL

private val loggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

private val httpClient =
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

private val retrofit =
    Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        )
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

object AppApi {
    val retrofitService: AppApiInterface by lazy {
        retrofit.create(AppApiInterface::class.java)
    }
}
