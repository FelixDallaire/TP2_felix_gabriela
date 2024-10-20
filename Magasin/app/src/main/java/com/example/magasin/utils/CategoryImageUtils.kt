package com.example.magasin.utils

import com.example.magasin.R

/**
 * Utilitaire pour récupérer les identifiants de ressources d'images en fonction des catégories de produits.
 */
object CategoryImageUtils {

    /**
     * Retourne l'identifiant de ressource drawable correspondant à une catégorie donnée.
     *
     * @param category La catégorie de l'article.
     * @return L'identifiant de ressource drawable correspondant.
     */
    fun getImageResIdForCategory(category: String): Int {
        return when (category) {
            "Légumes" -> R.drawable.vegetable_product
            "Viande" -> R.drawable.meat_product
            "Dessert" -> R.drawable.dessert_product
            "Oeufs" -> R.drawable.egg_product
            "Boisson" -> R.drawable.drink_product
            "Pain" -> R.drawable.bread_product
            else -> R.drawable.default_product
        }
    }
}
