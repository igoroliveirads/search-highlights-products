package com.example.searchhighlightsproducts

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.searchhighlightsproducts.databinding.ActivityItemDetailsBinding
import com.example.searchhighlightsproducts.databinding.ActivityItemListBinding
import com.squareup.picasso.Picasso

class ItemDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageBack.setOnClickListener {
            finish()
        }

        getInfo()
    }

    @SuppressLint("SetTextI18n")
    private fun getInfo(){
        val title = intent.getStringExtra(Constants.KEY.TITLE)
        val price = intent.getStringExtra(Constants.KEY.PRICE)
        val image = intent.getStringExtra(Constants.KEY.IMAGE)

        binding.textTitleItem.text = title
        binding.textPriceItem.text = "R$ $price"
        Picasso.get().load(image).into(binding.imageThumb)
    }
}