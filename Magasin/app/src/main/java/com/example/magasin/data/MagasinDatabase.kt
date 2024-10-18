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
                                            name = "Gourde Éco-responsable",
                                            description = "Gourde de 24 oz, sans BPA, en acier inoxydable, conçue pour garder les boissons froides jusqu'à 48 heures.",
                                            price = 25.99,
                                            category = "Produits Écologiques",
                                            quantity = 5,
                                            image = "eco_bottle.png"
                                        ),
                                        ShopItem(
                                            name = "T-shirt en Coton Biologique",
                                            description = "T-shirt doux, 100% coton biologique, en gris charbon, parfait pour les sorties décontractées ou formelles.",
                                            price = 30.99,
                                            category = "Vêtements",
                                            quantity = 3,
                                            image = "organic_tshirt.png"
                                        ),
                                        ShopItem(
                                            name = "Clavier Sans Fil en Bambou",
                                            description = "Clavier durable en bambou avec connectivité Bluetooth, compatible avec plusieurs systèmes d'exploitation.",
                                            price = 45.99,
                                            category = "Électronique",
                                            quantity = 10,
                                            image = "bamboo_keyboard.png"
                                        ),
                                        ShopItem(
                                            name = "Pot en Céramique Artisanal",
                                            description = "Pot en céramique fait main avec une glaçure artistique, idéal pour les plantes d'intérieur et d'extérieur.",
                                            price = 34.99,
                                            category = "Décor Maison",
                                            quantity = 7,
                                            image = "ceramic_planter.png"
                                        ),
                                        ShopItem(
                                            name = "Sac à Dos en Cuir Végan",
                                            description = "Sac à dos durable et élégant fabriqué à partir de cuir végan de haute qualité avec plusieurs poches.",
                                            price = 52.99,
                                            category = "Accessoires",
                                            quantity = 4,
                                            image = "vegan_backpack.png"
                                        ),
                                        ShopItem(
                                            name = "Grains de Café Gourmet",
                                            description = "1 lb de grains de café Arabica de première qualité, approvisionnés de manière éthique et torréfiés frais pour garantir la meilleure saveur.",
                                            price = 17.99,
                                            category = "Alimentation Gourmet",
                                            quantity = 15,
                                            image = "coffee_beans.png"
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


