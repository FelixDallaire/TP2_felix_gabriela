package com.example.magasin.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.magasin.model.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ShopItem::class], version = 1)
abstract class MagasinDatabase : RoomDatabase() {
    abstract fun shopItemDao(): ShopItemDao

    companion object {
        @Volatile
        private var INSTANCE: MagasinDatabase? = null

        fun getInstance(context: Context): MagasinDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MagasinDatabase::class.java,
                    "database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            Log.d("TAG", "onCreate: BD")

                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    val shopItems = listOf(
                                        ShopItem(
                                            name = "Item 1",
                                            description = "Description for Item 1",
                                            price = 10.99,
                                            category = "Category 1",
                                            quantity = 5,
                                            image = "image1.png"
                                        ),
                                        ShopItem(
                                            name = "Item 2",
                                            description = "Description for Item 2",
                                            price = 20.99,
                                            category = "Category 2",
                                            quantity = 3,
                                            image = "image2.png"
                                        ),
                                        ShopItem(
                                            name = "Item 3",
                                            description = "Description for Item 3",
                                            price = 15.99,
                                            category = "Category 3",
                                            quantity = 10,
                                            image = "image3.png"
                                        )
                                    )

                                    database.shopItemDao().insertShopItems(shopItems)
                                }
                            }

                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}


