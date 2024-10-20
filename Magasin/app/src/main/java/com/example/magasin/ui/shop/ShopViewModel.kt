package com.example.magasin.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.magasin.data.ShopItemDao
import com.example.magasin.model.ShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopViewModel(private val shopItemDao: ShopItemDao) : ViewModel() {
    val shopItems: LiveData<List<ShopItem>> = shopItemDao.getAllShopItems()

    fun addOrUpdateShopItem(shopItem: ShopItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if (shopItem.id == 0) {
                shopItemDao.insertShopItem(shopItem)
            } else {
                shopItemDao.updateShopItem(shopItem)
            }
        }
    }

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch(Dispatchers.IO) {
            shopItemDao.deleteShopItem(shopItem)
        }
    }
}
