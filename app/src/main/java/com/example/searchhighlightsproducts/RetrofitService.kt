package com.example.searchhighlightsproducts

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val ACCESS_TOKEN: String = "APP_USR-6008466615448127-091414-27c94fef65b4a8a26e8791f10996af1f-292249204"

interface RetrofitService {
    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("sites/MLB/domain_discovery/search?limit=1")
    fun categoriesList(@Query("q") categoryQuery: String): Call<List<CategoryPredictorEntity>>

    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("highlights/MLB/category/{category_id}")
    fun highlightsItems(@Path("category_id") categoryId: String): Call<HighlightsItemEntity>

    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("items")
    fun itemsList(@Query("ids") itemIds: String): Call<List<ItemEntity>>
}