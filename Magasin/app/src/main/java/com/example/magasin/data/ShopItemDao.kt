package com.example.magasin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.magasin.model.ShopItem

@Dao
interface ShopItemDao {

    @Insert
    fun insertShopItem(shopItem: ShopItem)

    @Insert
    fun insertShopItemReturnId(shopItem: ShopItem): Long

    @Insert
    fun insertShopItems(users: List<ShopItem>): List<Long>

    @Update
    fun updateUser(shopItem: ShopItem)

    @Delete
    fun deleteUser(shopItem: ShopItem)

    @Query("SELECT * FROM shop_items")
    fun getAllShopItems(): LiveData<List<ShopItem>>
}