package com.example.searchhighlightsproducts

import com.google.gson.annotations.SerializedName

class CategoryPredictorEntity {

    @SerializedName("domain_id")
    var domain_id: String = ""

    @SerializedName("domain_name")
    var domain_name: String = ""

    @SerializedName("category_id")
    var category_id: String = ""

    @SerializedName("category_name")
    var category_name: String = ""
}

/*

https://api.mercadolibre.com/sites/MLB/domain_discovery/search?limit=1&q=carro

[
{
    "domain_id": "MLB-SCALEXTRIC_CARS",
    "domain_name": "Carros de autorama",
    "category_id": "MLB9190",
    "category_name": "Carros",
    "attributes": []
}
]*/
