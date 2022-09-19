package com.example.searchhighlightsproducts.api

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {

        private lateinit var INSTANCE: Retrofit
        private const val BASE_URL = "https://api.mercadolibre.com/"

        private fun getRetrofitInstance(): Retrofit {
            val http = OkHttpClient.Builder()
            if (!Companion::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(http.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE
        }

        fun createRetrofitService(): RetrofitService {
            return getRetrofitInstance().create(RetrofitService::class.java)
        }
    }
}