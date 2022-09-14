package com.example.searchhighlightsproducts

import com.google.gson.annotations.SerializedName

class HighlightsItemEntity {

    @SerializedName("content")
    lateinit var content: List<HighlightsItemContent>
}

class HighlightsItemContent {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("position")
    var position: Int = 0

    @SerializedName("type")
    var type: String = ""
}