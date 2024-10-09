package com.example.magasin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShopItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "price")
    var price: Double,

    @ColumnInfo(name = "category")
    var category: String = "",

    @ColumnInfo(name = "quantity")
    var quantity: Int = 1,

    @ColumnInfo(name = "image")
    var image: String
)