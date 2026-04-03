package edu.nd.pmcburne.hello.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("placemarks.json")
    suspend fun getPlacements(): List<PlaceDTO>

    companion object {
        private const val BASE_URL = "https://www.cs.virginia.edu/~wxt4gm/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
