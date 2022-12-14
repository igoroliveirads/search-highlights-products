package com.example.searchhighlightsproducts.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.searchhighlightsproducts.R
import com.example.searchhighlightsproducts.api.ItemDescription
import com.example.searchhighlightsproducts.api.RetrofitClient
import com.example.searchhighlightsproducts.databinding.ActivityItemDetailsBinding
import com.example.searchhighlightsproducts.infra.Constants
import com.example.searchhighlightsproducts.infra.FavoritePreferences
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageBack.setOnClickListener {
            finish()
        }

        val itemId = intent.getStringExtra(Constants.KEY.ID)
        if(FavoritePreferences(this).contains(itemId ?: "")){
            addFavoriteItem(itemId ?: "")
        } else{
            removeFavoriteItem(itemId ?: "")
        }

        getInfo()
    }

    @SuppressLint("SetTextI18n")
    private fun getInfo(){
        val id = intent.getStringExtra(Constants.KEY.ID)
        val title = intent.getStringExtra(Constants.KEY.TITLE)
        val price = intent.getStringExtra(Constants.KEY.PRICE)
        val image = intent.getStringExtra(Constants.KEY.IMAGE)

        getDescription(id ?: "")
        binding.textTitleItem.text = title
        binding.textPriceItem.text = "R$ $price"
        Picasso.get().load(image).into(binding.imageThumb)
    }

    private fun getDescription(itemId: String){
        val service = RetrofitClient.createRetrofitService()
        val call: Call<ItemDescription> = service.itemDescription(itemId)
        call.enqueue(object : Callback<ItemDescription>{
            override fun onResponse(
                call: Call<ItemDescription>,
                response: Response<ItemDescription>
            ) {
                if (response.isSuccessful) {
                    val description = response.body()
                    if (description != null){
                        binding.textDescription.text = description.plain_text
                    } else {
                        Toast.makeText(baseContext, getString(R.string.description_processing_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ItemDescription>, t: Throwable) {
                Log.e(Constants.KEY.ERROR, "getDescription(): $t")
            }

        })
    }

    private fun addFavoriteItem(itemId: String) {
        binding.textFavorite.text = getString(R.string.remove_favorites)
        binding.textFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite, 0, 0, 0)
        binding.textFavorite.setOnClickListener{
            FavoritePreferences(this).addFavorite(itemId)
            removeFavoriteItem(itemId)
        }
    }

    private fun removeFavoriteItem(itemId: String) {
        binding.textFavorite.text = getString(R.string.add_favorites)
        binding.textFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_off, 0, 0, 0)
        binding.textFavorite.setOnClickListener {
            FavoritePreferences(this).removeFavorite(itemId)
            addFavoriteItem(itemId)
        }
    }
}