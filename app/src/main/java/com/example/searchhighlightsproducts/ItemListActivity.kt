package com.example.searchhighlightsproducts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        getCategory()
        getHighlights()
        getItems()
    }

    private fun getCategory(categoryQuery: String = "carro") {
        val service = RetrofitClient.createRetrofitService()
        val call: Call<List<CategoryPredictorEntity>> = service.categoriesList(categoryQuery)
        call.enqueue(object : Callback<List<CategoryPredictorEntity>> {
            override fun onResponse(
                call: Call<List<CategoryPredictorEntity>>,
                response: Response<List<CategoryPredictorEntity>>
            ) {
                val categories = response.body()
                // TODO: "TRATAR EM CASO DE ERRO DE SERVIDOR"
                if (categories != null) {
                    Log.d("CATEGORY", "Category ID: ${categories[0].category_id}")
                } else {
                    // TODO("Not yet implemented")
                }
            }

            override fun onFailure(call: Call<List<CategoryPredictorEntity>>, t: Throwable) {
                // TODO("Not yet implemented")
            }
        })
    }

    private fun getHighlights(categoryId: String = "MLB9190") {
        val service = RetrofitClient.createRetrofitService()
        val call: Call<HighlightsItemEntity> = service.highlightsItems(categoryId)
        call.enqueue(object : Callback<HighlightsItemEntity> {
            override fun onResponse(
                call: Call<HighlightsItemEntity>,
                response: Response<HighlightsItemEntity>
            ) {
                val highlights = response.body()
                // TODO: "TRATAR EM CASO DE ERRO DE SERVIDOR"
                if (highlights != null){
                    Log.d("HIGHLIGHT", "Highlight ID: ${highlights.content[0].id}")
                } else {
                    // TODO("Not yet implemented")
                }
            }

            override fun onFailure(call: Call<HighlightsItemEntity>, t: Throwable) {
                // TODO("Not yet implemented")
            }
        })
    }

    private fun getItems(itemIds: String = "MLB2223703098") {
        val service = RetrofitClient.createRetrofitService()
        val call: Call<List<ItemEntity>> = service.itemsList(itemIds)
        call.enqueue(object : Callback<List<ItemEntity>>{
            override fun onResponse(
                call: Call<List<ItemEntity>>,
                response: Response<List<ItemEntity>>
            ) {
                val items = response.body()
                // TODO: "TRATAR EM CASO DE ERRO DE SERVIDOR"
                if (items != null){
                    Log.d("ITEM", "Item title: ${items[0].title}")
                } else {
                    // TODO("Not yet implemented")
                }
            }

            override fun onFailure(call: Call<List<ItemEntity>>, t: Throwable) {
                // TODO("Not yet implemented")
            }

        })
    }
}