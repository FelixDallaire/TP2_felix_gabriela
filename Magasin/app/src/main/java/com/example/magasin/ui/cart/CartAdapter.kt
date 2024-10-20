package com.example.magasin.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magasin.R
import com.example.magasin.databinding.CartItemBinding
import com.example.magasin.model.ShopItem

class CartAdapter(var cartItems: MutableList<ShopItem>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int = cartItems.size

    class ViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: ShopItem) {
            binding.tvCartProductName.text = cartItem.name
            binding.tvCartProductQuantity.text = "Qty: ${cartItem.quantity}"

            val imageResId = when (cartItem.category) {
                "Légumes" -> R.drawable.vegetable_product
                "Viande" -> R.drawable.meat_product
                "Dessert" -> R.drawable.dessert_product
                "Oeufs" -> R.drawable.egg_product
                "Boisson" -> R.drawable.drink_product
                "Pain" -> R.drawable.bread_product
                else -> R.drawable.default_product
            }
            binding.ivCartProductImage.setImageResource(imageResId)
        }
    }
}
