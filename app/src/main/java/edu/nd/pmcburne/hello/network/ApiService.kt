package edu.nd.pmcburne.hello.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//defines API endpoints for network requests using Retrofit
interface ApiService {
    //sends a GET request to "placemarks.json"
    @GET("placemarks.json")
    suspend fun getPlacements(): List<PlaceDTO>

    companion object {
        //base URL for all API requests
        private const val BASE_URL = "https://www.cs.virginia.edu/~wxt4gm/"

        //creates and configures a retrofit instance for this API
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
