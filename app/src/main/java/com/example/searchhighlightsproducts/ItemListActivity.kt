package com.example.searchhighlightsproducts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        getCategory("carro")
    }

    private fun initRecyclerItems(itemList: List<ItemEntity>) {
        binding.recyclerViewItem.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItem.setHasFixedSize(true)
        binding.recyclerViewItem.adapter = ItemAdapter(itemList) { item ->
            val intent = Intent(this, ItemDetailsActivity::class.java)
            intent.putExtra(Constants.KEY.ITEM_KEY, item.body.title)
            startActivity(intent)
        }
    }

    private fun getCategory(search: String) {
        val service = RetrofitClient.createRetrofitService()
        val call: Call<List<CategoryPredictorEntity>> = service.categoriesList(search)
        call.enqueue(object : Callback<List<CategoryPredictorEntity>> {
            override fun onResponse(
                call: Call<List<CategoryPredictorEntity>>,
                response: Response<List<CategoryPredictorEntity>>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()
                    if (categories != null) {
                        getHighlights(categories[0].category_id)
                        binding.circularProgressIndicator.visibility = View.GONE
                    } else {
                        Toast.makeText(baseContext, "Produto não encontrado.", Toast.LENGTH_SHORT)
                            .show()
                        binding.circularProgressIndicator.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(
                        baseContext,
                        "500 - Erro interno no servidor.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.circularProgressIndicator.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<List<CategoryPredictorEntity>>, t: Throwable) {
                Toast.makeText(baseContext, "Sem conexão com a internet.", Toast.LENGTH_SHORT)
                    .show()
                binding.circularProgressIndicator.visibility = View.GONE
            }
        })
    }

    private fun getHighlights(categoryId: String) {
        val service = RetrofitClient.createRetrofitService()
        val call: Call<HighlightsItemEntity> = service.highlightsItems(categoryId)

        call.enqueue(object : Callback<HighlightsItemEntity> {
            override fun onResponse(
                call: Call<HighlightsItemEntity>,
                response: Response<HighlightsItemEntity>
            ) {
                if (response.isSuccessful) {
                    val highlights = response.body()
                    if (highlights != null) {
                        val itemIds = highlights.content.filter { it.type == "ITEM" }.map { it.id }
                        getItems(itemIds)
                        binding.circularProgressIndicator.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(baseContext, "Produto não encontrado.", Toast.LENGTH_SHORT)
                        .show()
                    binding.circularProgressIndicator.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<HighlightsItemEntity>, t: Throwable) {
                Toast.makeText(baseContext, "Produto não encontrado.", Toast.LENGTH_SHORT)
                    .show()
                binding.circularProgressIndicator.visibility = View.GONE
            }
        })
    }

    private fun getItems(itemIds: List<String>) {
        val service = RetrofitClient.createRetrofitService()
        val items = itemIds.joinToString(",")
        val call: Call<List<ItemEntity>> = service.itemsList(items)
        call.enqueue(object : Callback<List<ItemEntity>> {
            override fun onResponse(
                call: Call<List<ItemEntity>>,
                response: Response<List<ItemEntity>>
            ) {
                if (response.isSuccessful) {
                    val itemList: List<ItemEntity> = response.body() ?: emptyList()
                    initRecyclerItems(itemList)
                }
            }

            override fun onFailure(call: Call<List<ItemEntity>>, t: Throwable) {
                Toast.makeText(baseContext, "Produto não encontrado.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}