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

/**
 * Base de données Room pour la gestion des articles de magasin dans l'application.
 * @property entities Liste des entités incluses dans la base de données, actuellement seulement [ShopItem].
 * @property version Version de la base de données. Si vous changez le schéma, vous devez incrémenter le numéro de version.
 */
@Database(entities = [ShopItem::class], version = 1)
abstract class MagasinDatabase : RoomDatabase() {
    abstract fun shopItemDao(): ShopItemDao

    companion object {
        @Volatile
        private var INSTANCE: MagasinDatabase? = null

        /**
         * Instance singleton de [MagasinDatabase].
         * @param context Contexte de l'application.
         * @return L'instance singleton de la base de données.
         */
        fun getInstance(context: Context): MagasinDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MagasinDatabase::class.java,
                    "myoncart"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("Database", "onCreate: Database created - populating with initial data.")
                            INSTANCE?.let { database ->
                                populateInitialData(database)
                            }
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }

        /**
         * Remplit la base de données avec des données initiales.
         * Cette fonction est appelée lorsque la base de données est créée pour la première fois.
         * Elle précharge la base de données avec une liste prédéfinie d'articles de magasin.
         *
         * @param database L'instance de [MagasinDatabase] à peupler.
         */
        private fun populateInitialData(database: MagasinDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                val shopItems = listOf(
                    ShopItem(name = "Poivron Rouge", description = "Un poivron rouge frais et croquant, parfait pour les salades et les plats cuisinés.", price = 1.99, category = "Légumes", quantity = 20),
                    ShopItem(name = "Carotte Bio", description = "Des carottes biologiques croquantes, riches en bêta-carotène et en nutriments.", price = 0.99, category = "Légumes", quantity = 25),
                    ShopItem(name = "Poulet Frit", description = "Poulet parfaitement croustillant, un délice pour tout repas.", price = 7.99, category = "Viande", quantity = 15),
                    ShopItem(name = "Porc Grillé", description = "Porc grillé juteux et tendre, assaisonné avec un mélange d'épices.", price = 8.50, category = "Viande", quantity = 10),
                    ShopItem(name = "Glace au Chocolat", description = "Glace au chocolat riche faite avec le meilleur cacao.", price = 3.50, category = "Dessert", quantity = 30),
                    ShopItem(name = "Cheesecake", description = "Cheesecake onctueux avec une base croustillante de biscuits Graham.", price = 4.00, category = "Dessert", quantity = 20),
                    ShopItem(name = "Oeufs à la Coque", description = "Oeufs parfaitement cuits à la coque, idéals pour une collation rapide ou comme partie d'un petit déjeuner nutritif.", price = 1.20, category = "Oeufs", quantity = 50),
                    ShopItem(name = "Oeufs Brouillés", description = "Oeufs brouillés moelleux et légers, cuits avec un peu de lait et de beurre.", price = 1.50, category = "Oeufs", quantity = 40),
                    ShopItem(name = "Limonade Fraîche", description = "Limonade rafraîchissante faite à partir de citrons fraîchement pressés.", price = 2.00, category = "Boisson", quantity = 25),
                    ShopItem(name = "Thé Glacé", description = "Thé glacé frais et rafraîchissant, la boisson parfaite pour une journée chaude.", price = 1.75, category = "Boisson", quantity = 30),
                    ShopItem(name = "Pain Fraîchement Cuit", description = "Pain chaud, fraîchement sorti du four avec une croûte parfaite.", price = 2.50, category = "Pain", quantity = 30),
                    ShopItem(name = "Pain Complet", description = "Pain complet sain, doux à l'intérieur avec une croûte ferme.", price = 2.75, category = "Pain", quantity = 25)
                )
                database.shopItemDao().insertShopItems(shopItems)
            }
        }
    }
}
