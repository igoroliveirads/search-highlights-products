package com.example.searchhighlightsproducts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val service = RetrofitClient.createRetrofitService()
        val call: Call<List<CategoryPredictorEntity>> = service.domainDiscovery("carro")
        call.enqueue(object : Callback<List<CategoryPredictorEntity>>{
            override fun onResponse(
                call: Call<List<CategoryPredictorEntity>>,
                response: Response<List<CategoryPredictorEntity>>
            ) {
                val list = response.body()
            }

            override fun onFailure(call: Call<List<CategoryPredictorEntity>>, t: Throwable) {
                val s = ""
            }

        })

        val s = ""
        val abc = 123


    }
}