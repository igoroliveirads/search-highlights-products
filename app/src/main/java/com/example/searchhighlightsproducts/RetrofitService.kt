package com.example.searchhighlightsproducts

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val ACCESS_TOKEN: String = "APP_USR-6008466615448127-091408-1308d2d25d6ec5cb3623dc95a67f1206-292249204"

interface RetrofitService {
    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("sites/MLB/domain_discovery/search?limit=1")
    fun domainDiscovery(@Query("q") categoryQuery: String): Call<List<CategoryPredictorEntity>>
}