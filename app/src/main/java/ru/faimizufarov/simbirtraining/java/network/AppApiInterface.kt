package ru.faimizufarov.simbirtraining.java.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.faimizufarov.simbirtraining.java.data.models.CategoryResponse
import ru.faimizufarov.simbirtraining.java.data.models.NewsResponse

interface AppApiInterface {
    @GET("categories")
    suspend fun getCategories(): List<CategoryResponse>

    @POST("events")
    suspend fun getEvents(
        @Body ids: List<String>,
    ): List<NewsResponse>
}
