package com.example.searchhighlightsproducts

data class ItemModel(
    val id: String,
    val name: String,
    val price: Float,
    val description: String,
    val image: String,
)

object ItemList {
    fun getItemList(): MutableList<ItemModel> {
        return mutableListOf(
            ItemModel(
                "MLB1",
                "Bola de futebol",
                2.4F,
                "Uma bola de futebola amarela e branca",
                "https://img.freepik.com/vetores-gratis/imagem-preta-branca-realistica-da-bola-de-futebol_1284-8506.jpg?w=2000"
            ),
            ItemModel(
                "MLB1",
                "Bola de volei",
                2.4F,
                "Uma bola de futebola amarela e branca",
                "https://img.freepik.com/vetores-gratis/imagem-preta-branca-realistica-da-bola-de-futebol_1284-8506.jpg?w=2000"
            ),
            ItemModel(
                "MLB1",
                "Bola basquete",
                2.4F,
                "Uma bola de futebola amarela e branca",
                "https://img.freepik.com/vetores-gratis/imagem-preta-branca-realistica-da-bola-de-futebol_1284-8506.jpg?w=2000"
            )
        )
    }
}