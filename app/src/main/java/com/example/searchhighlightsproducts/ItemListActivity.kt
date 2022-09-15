package com.example.searchhighlightsproducts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchhighlightsproducts.databinding.ActivityItemListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val teste = getCategory("carro") {
            when (it) {
                is Result.Error -> it.error
                is Result.Success -> it.result
            }
        }
        initRecyclerItems()
    }

    private fun initRecyclerItems() {
        binding.recyclerViewItem.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItem.setHasFixedSize(true)
        binding.recyclerViewItem.adapter = ItemAdapter(ItemList.getItemList()) { item ->
            val intent = Intent(this, ItemDetailsActivity::class.java)
            intent.putExtra(Constants.KEY.ITEM_KEY, item.name)
            intent.putExtra(Constants.KEY.ITEM_KEY, item.price)
            startActivity(intent)
        }
    }

    private fun getCategory(search: String, onResult: (Result<List<ItemEntity>>) -> Unit) {
        val service = RetrofitClient.createRetrofitService()
        val call: Call<List<CategoryPredictorEntity>> = service.categoriesList(search)
        call.enqueue(object : Callback<List<CategoryPredictorEntity>> {
            override fun onResponse(
                call: Call<List<CategoryPredictorEntity>>,
                response: Response<List<CategoryPredictorEntity>>
            ) {
                val categories = response.body()
                // TODO: "TRATAR EM CASO DE ERRO DE SERVIDOR"
                if (categories != null) {
                    getHighlights(categories[0].category_id, onResult)
                } else {
                    // TODO("Not yet implemented")
                }
            }

            override fun onFailure(call: Call<List<CategoryPredictorEntity>>, t: Throwable) {
                onResult(Result.Error(t))
            }
        })
    }

    private fun getHighlights(categoryId: String, onResult: (Result<List<ItemEntity>>) -> Unit) {
        val service = RetrofitClient.createRetrofitService()
        val call: Call<HighlightsItemEntity> = service.highlightsItems(categoryId)

        call.enqueue(object : Callback<HighlightsItemEntity> {
            override fun onResponse(
                call: Call<HighlightsItemEntity>,
                response: Response<HighlightsItemEntity>
            ) {
                val highlights = response.body()
                // TODO: "TRATAR EM CASO DE ERRO DE SERVIDOR"
                if (highlights != null) {
                    val itemIds = highlights.content.filter { it.type == "ITEM" }.map { it.id }
                    getItems(itemIds, onResult)
                } else {
                    // TODO("Not yet implemented")
                }
            }

            override fun onFailure(call: Call<HighlightsItemEntity>, t: Throwable) {
                onResult(Result.Error(t))
            }
        })
    }

    private fun getItems(itemIds: List<String>, onResult: (Result<List<ItemEntity>>) -> Unit) {
        val service = RetrofitClient.createRetrofitService()
        val itemIds = itemIds.joinToString(",")
        val call: Call<List<ItemEntity>> = service.itemsList(itemIds)
        call.enqueue(object : Callback<List<ItemEntity>> {
            override fun onResponse(
                call: Call<List<ItemEntity>>,
                response: Response<List<ItemEntity>>
            ) {
                val items: List<ItemEntity> = response.body() ?: emptyList()
                onResult(Result.Success(items))
            }

            override fun onFailure(call: Call<List<ItemEntity>>, t: Throwable) {
                onResult(Result.Error(t))
            }
        })
    }
}