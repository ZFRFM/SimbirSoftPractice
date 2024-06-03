package ru.faimizufarov.simbirtraining.java.network

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.faimizufarov.simbirtraining.java.data.models.CategoryResponse
import ru.faimizufarov.simbirtraining.java.data.models.NewsResponse

interface AppApiInterface {
    @GET("categories")
    fun getCategories(): Observable<List<CategoryResponse>>

    @POST("events")
    fun getEvents(
        @Body ids: List<String>,
    ): Observable<List<NewsResponse>>
}
