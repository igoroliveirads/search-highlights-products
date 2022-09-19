package com.example.searchhighlightsproducts.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/* ACCESS_TOKEN possui validade de 6 horas */
const val ACCESS_TOKEN: String = "APP_USR-6008466615448127-091908-62fe4c7e8f5d4cc9694f82aca8d9eb9a-292249204"

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