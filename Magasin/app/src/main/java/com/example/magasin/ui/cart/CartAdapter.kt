package com.example.magasin.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magasin.R
import com.example.magasin.databinding.CartItemBinding
import com.example.magasin.model.ShopItem
import com.example.magasin.utils.CategoryImageUtils

/**
 * Un adapter pour RecyclerView qui gère les articles du panier.
 * @property cartItems La liste mutable des articles du panier.
 */
class CartAdapter(var cartItems: MutableList<ShopItem>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    /**
     * Crée et retourne un ViewHolder pour un élément du RecyclerView.
     * @param parent Le groupe de vues parent dans lequel la nouvelle vue sera ajoutée après avoir été liée à une position adaptateur.
     * @param viewType Le type de vue de la nouvelle Vue.
     * @return Un nouveau ViewHolder qui contient la vue pour l'élément.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * Associe les données de l'élément avec le ViewHolder à une position donnée dans le RecyclerView.
     * @param holder Le ViewHolder qui doit être mis à jour pour représenter le contenu de l'élément à la position donnée dans l'ensemble de données.
     * @param position La position de l'élément dans l'ensemble de données de l'adaptateur.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }

    /**
     * Retourne la taille de l'ensemble de données (invocateurs).
     * @return Le nombre total d'éléments dans cet adaptateur.
     */
    override fun getItemCount(): Int = cartItems.size


    /**
     * ViewHolder qui décrit une vue d'élément et des métadonnées sur son emplacement dans le RecyclerView.
     */
    class ViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Associe un article du panier spécifié à ce ViewHolder.
         * @param cartItem L'article du panier à associer.
         */
        fun bind(cartItem: ShopItem) {
            binding.tvCartProductName.text = cartItem.name
            binding.tvCartProductQuantity.text =
                itemView.context.getString(R.string.cart_product_quantity, cartItem.quantity)

            val imageResId = CategoryImageUtils.getImageResIdForCategory(cartItem.category)
            binding.ivCartProductImage.setImageResource(imageResId)
        }
    }
}
