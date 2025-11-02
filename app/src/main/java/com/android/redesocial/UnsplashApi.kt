package com.android.redesocial

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// --- MODELOS DE DADOS ---
data class UnsplashPhoto(val urls: Urls)
data class Urls(val regular: String)

// --- INTERFACE DA API ---
interface UnsplashApi {
    @GET("photos/random")
    suspend fun getRandomPhoto(
        @Query("client_id") clientId: String,
        @Query("query") query: String = "people",
        @Query("orientation") orientation: String = "landscape"
    ): UnsplashPhoto
}

// --- SINGLETON Retrofit ---
object RetrofitInstance {
    val api: UnsplashApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashApi::class.java)
    }
}