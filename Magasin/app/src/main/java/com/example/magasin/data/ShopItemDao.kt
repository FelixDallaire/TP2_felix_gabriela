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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShopItems(shopItems: List<ShopItem>): List<Long>

    @Update
    fun updateShopItem(shopItem: ShopItem)

    @Delete
    fun deleteShopItem(shopItem: ShopItem)

    @Query("SELECT * FROM shop_items")
    fun getAllShopItems(): LiveData<List<ShopItem>>
}
