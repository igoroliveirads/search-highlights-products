package com.example.searchhighlightsproducts

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.searchhighlightsproducts.databinding.ActivityItemDetailsBinding
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
                        Toast.makeText(baseContext, "Erro ao processar a descrição do produto1.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ItemDescription>, t: Throwable) {
                Log.e("ERROR", "getDescription(): $t")
            }

        })
    }

    private fun addFavoriteItem(itemId: String) {
        binding.textFavorite.text = "Remover dos favoritos"
        binding.textFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite, 0, 0, 0)
        binding.textFavorite.setOnClickListener{
            FavoritePreferences(this).addFavorite(itemId)
            removeFavoriteItem(itemId)
        }
    }

    private fun removeFavoriteItem(itemId: String) {
        binding.textFavorite.text = "Adicionar aos favoritos"
        binding.textFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_off, 0, 0, 0)
        binding.textFavorite.setOnClickListener {
            FavoritePreferences(this).removeFavorite(itemId)
            addFavoriteItem(itemId)
        }
    }
}