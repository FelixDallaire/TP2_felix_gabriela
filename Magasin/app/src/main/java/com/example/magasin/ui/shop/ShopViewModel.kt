package com.example.magasin.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.magasin.data.ShopItemDao
import com.example.magasin.model.ShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel pour gérer les données des articles de magasin dans l'interface utilisateur du magasin.
 * Fournit des méthodes pour ajouter, mettre à jour et supprimer des articles via le DAO.
 * @param shopItemDao DAO pour accéder aux opérations de la base de données des articles de magasin.
 */
class ShopViewModel(private val shopItemDao: ShopItemDao) : ViewModel() {
    /**
     * Les articles de magasin disponibles, observables via LiveData.
     */
    val shopItems: LiveData<List<ShopItem>> = shopItemDao.getAllShopItems()

    /**
     * Ajoute ou met à jour un article de magasin dans la base de données.
     * Si l'article a un ID de 0, un nouvel article est inséré, sinon l'article existant est mis à jour.
     * @param shopItem L'article de magasin à ajouter ou mettre à jour.
     */
    fun addOrUpdateShopItem(shopItem: ShopItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if (shopItem.id == 0) {
                shopItemDao.insertShopItem(shopItem)
            } else {
                shopItemDao.updateShopItem(shopItem)
            }
        }
    }

    /**
     * Supprime un article de magasin de la base de données.
     * @param shopItem L'article de magasin à supprimer.
     */
    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch(Dispatchers.IO) {
            shopItemDao.deleteShopItem(shopItem)
        }
    }
}
