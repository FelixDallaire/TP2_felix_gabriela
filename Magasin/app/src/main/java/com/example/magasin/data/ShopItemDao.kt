package com.example.magasin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.magasin.model.ShopItem

/**
 * DAO (Data Access Object) pour accéder aux [ShopItem] dans la base de données.
 * Fournit des méthodes pour insérer, mettre à jour, supprimer et récupérer les articles de magasin.
 */
@Dao
interface ShopItemDao {

    /**
     * Insère un [ShopItem] dans la base de données.
     * @param shopItem L'objet [ShopItem] à insérer.
     */
    @Insert
    fun insertShopItem(shopItem: ShopItem)

    /**
     * Insère un [ShopItem] dans la base de données et retourne l'ID généré.
     * @param shopItem L'objet [ShopItem] à insérer.
     * @return L'ID généré pour l'élément inséré.
     */
    @Insert
    fun insertShopItemReturnId(shopItem: ShopItem): Long

    /**
     * Insère une liste de [ShopItem] dans la base de données. Remplace les éléments en conflit.
     * @param shopItems La liste des [ShopItem] à insérer.
     * @return La liste des IDs générés pour les éléments insérés.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShopItems(shopItems: List<ShopItem>): List<Long>

    /**
     * Met à jour un [ShopItem] existant dans la base de données.
     * @param shopItem L'objet [ShopItem] à mettre à jour.
     */
    @Update
    fun updateShopItem(shopItem: ShopItem)

    /**
     * Supprime un [ShopItem] de la base de données.
     * @param shopItem L'objet [ShopItem] à supprimer.
     */
    @Delete
    fun deleteShopItem(shopItem: ShopItem)

    /**
     * Récupère tous les [ShopItem] de la base de données.
     * @return Une [LiveData] contenant la liste de tous les [ShopItem].
     */
    @Query("SELECT * FROM shop_items")
    fun getAllShopItems(): LiveData<List<ShopItem>>
}
