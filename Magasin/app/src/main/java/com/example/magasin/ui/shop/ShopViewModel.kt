package com.example.magasin.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShopViewModel : ViewModel() {

    private val _shopItems = MutableLiveData<List<ShopItem>>().apply {
        value = listOf(
            ShopItem("Produit 1", "Description du Produit 1", 19.99, "image1.jpg"),
            ShopItem("Produit 2", "Description du Produit 2", 29.99, "image2.jpg"),
            ShopItem("Produit 3", "Description du Produit 3", 39.99, "image3.jpg"),
            ShopItem("Produit 4", "Description du Produit 4", 49.99, "image4.jpg"),
            ShopItem("Produit 5", "Description du Produit 5", 59.99, "image5.jpg"),
            ShopItem("Produit 6", "Description du Produit 6", 69.99, "image6.jpg"),
            ShopItem("Produit 7", "Description du Produit 7", 79.99, "image7.jpg"),
            ShopItem("Produit 8", "Description du Produit 8", 89.99, "image8.jpg"),
            ShopItem("Produit 9", "Description du Produit 9", 99.99, "image9.jpg"),
            ShopItem("Produit 10", "Description du Produit 10", 100.99, "image10.jpg")
        )
    }
    val shopItems: LiveData<List<ShopItem>> = _shopItems
}
