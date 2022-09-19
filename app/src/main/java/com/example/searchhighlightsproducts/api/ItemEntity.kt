package com.example.searchhighlightsproducts.api

import com.google.gson.annotations.SerializedName

class ItemEntity {

    @SerializedName("body")
    lateinit var body: ItemBody

}

class ItemBody {

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

class ItemDescription {

    @SerializedName("plain_text")
    var plain_text: String = ""
}

