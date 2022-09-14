package com.example.searchhighlightsproducts

import com.google.gson.annotations.SerializedName

class ItemEntity {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("title")
    var title: String = ""

    @SerializedName("category_id")
    var category_id: String = ""

    @SerializedName("price")
    var price: Float = 0.0F

    @SerializedName("secure_thumbnail")
    var secure_thumbnail: String = ""

}

