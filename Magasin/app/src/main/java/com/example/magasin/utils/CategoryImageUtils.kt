package com.example.magasin.utils

import com.example.magasin.R

object CategoryImageUtils {

    /**
     * Returns the drawable resource ID for a given category.
     *
     * @param category The category of the item.
     * @return The corresponding drawable resource ID.
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
