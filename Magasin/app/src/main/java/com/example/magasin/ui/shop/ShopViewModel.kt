package com.example.magasin.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.magasin.data.ShopItemDao
import com.example.magasin.model.ShopItem

class ShopViewModel(private val shopItemDao: ShopItemDao) : ViewModel() {
    val shopItems: LiveData<List<ShopItem>> = shopItemDao.getAllShopItems()
}
