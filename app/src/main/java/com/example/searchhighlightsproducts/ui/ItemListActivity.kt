package com.example.searchhighlightsproducts.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchhighlightsproducts.api.CategoryPredictorEntity
import com.example.searchhighlightsproducts.api.HighlightsItemEntity
import com.example.searchhighlightsproducts.api.ItemEntity
import com.example.searchhighlightsproducts.api.RetrofitClient
import com.example.searchhighlightsproducts.databinding.ActivityItemListBinding
import com.example.searchhighlightsproducts.infra.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        search()
    }

    private fun search(){
        binding.editSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.editSearch.clearFocus()
                binding.circularProgressIndicator.visibility = View.VISIBLE
                getCategory(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
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
                        Toast.makeText(baseContext, "Produto não encontrado.", Toast.LENGTH_LONG).show()
                        binding.circularProgressIndicator.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(baseContext,"Erro interno no servidor. Tente novamente mais tarde.",Toast.LENGTH_LONG).show()
                    binding.circularProgressIndicator.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<List<CategoryPredictorEntity>>, t: Throwable) {
                Toast.makeText(baseContext, "Sem conexão com a internet.", Toast.LENGTH_LONG).show()
                Log.e("ERROR", "getCategory(): $t")
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
                    }
                } else {
                    Toast.makeText(baseContext, "Produto não encontrado.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<HighlightsItemEntity>, t: Throwable) {
                Log.e("ERROR", "getHighlights(): $t")
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
                } else {
                    Toast.makeText(baseContext, "Erro interno no servidor. Tente novamente mais tarde.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<ItemEntity>>, t: Throwable) {
                Log.e("ERROR", "getItems(): $t")
            }
        })
    }

    private fun initRecyclerItems(itemList: List<ItemEntity>) {
        binding.recyclerViewItem.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItem.setHasFixedSize(true)
        binding.recyclerViewItem.adapter = ItemAdapter(itemList) { item ->
            val intent = Intent(this, ItemDetailsActivity::class.java)
            intent.putExtra(Constants.KEY.ID, item.body.id)
            intent.putExtra(Constants.KEY.TITLE, item.body.title)
            intent.putExtra(Constants.KEY.PRICE, item.body.price.toString())
            intent.putExtra(Constants.KEY.IMAGE, item.body.secure_thumbnail)
            startActivity(intent)
        }
    }
}