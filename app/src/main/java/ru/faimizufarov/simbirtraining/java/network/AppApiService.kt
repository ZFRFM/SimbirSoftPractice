package ru.faimizufarov.simbirtraining.java.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import ru.faimizufarov.simbirtraining.java.data.models.CategoryResponse

private const val BASE_URL = "http://192.168.42.86:8080"

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
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

interface AppApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryResponse>

    @POST("events")
    fun getEvents(): Flow<List<Unit>>
}

object AppApi {
    val retrofitService: AppApiService by lazy {
        retrofit.create(AppApiService::class.java)
    }
}
