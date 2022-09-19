package com.example.searchhighlightsproducts

import android.content.Context
import android.content.SharedPreferences

class FavoritePreferences(context: Context) {

    private val storage: SharedPreferences =
        context.getSharedPreferences(Constants.PREFERENCES.SHARED_KEY, 0)

    fun addFavorite(itemId: String) {
        storage.edit().putString(Constants.PREFERENCES.ITEM_KEY, itemId).apply()
    }

    fun removeFavorite(itemId: String) {
        storage.edit().remove(Constants.PREFERENCES.ITEM_KEY).apply()
    }

    fun contains(productId: String): Boolean {
        return storage.contains(Constants.PREFERENCES.ITEM_KEY)
    }
}