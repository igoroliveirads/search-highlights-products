package com.example.searchhighlightsproducts

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/* ACCESS_TOKEN possui validade de 6 horas */
const val ACCESS_TOKEN: String = "APP_USR-6008466615448127-091815-2c664ee91eacf0f3e9421f7f1f1c1183-292249204"

interface RetrofitService {

    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("sites/MLB/domain_discovery/search?limit=1")
    fun categoriesList(@Query("q") search: String): Call<List<CategoryPredictorEntity>>

    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("highlights/MLB/category/{category_id}")
    fun highlightsItems(@Path("category_id") categoryId: String): Call<HighlightsItemEntity>

    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("items")
    fun itemsList(@Query("ids") itemIds: String): Call<List<ItemEntity>>

    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("items/{item_id}/description")
    fun itemDescription(@Path("item_id") itemId: String): Call<ItemDescription>

}