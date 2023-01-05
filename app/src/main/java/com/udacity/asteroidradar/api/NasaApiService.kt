package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.ImageOfTheDay
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface AsteroidsApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getProperties(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Response<JSONObject>
}

interface ImageOfTheDayApiService {
    @GET("planetary/apod")
    suspend fun getProperties(@Query("api_key") apiKey: String): ImageOfTheDay
}

object AsteroidsApi {
    val retrofitService: AsteroidsApiService by lazy { retrofit.create(AsteroidsApiService::class.java) }
}

object ImageOfTheDayApi {
    val retrofitService: ImageOfTheDayApiService by lazy { retrofit.create(ImageOfTheDayApiService::class.java) }
}
