package com.example.magasin.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magasin.databinding.CartItemBinding
import com.example.magasin.model.ShopItem

class CartAdapter(private var cartItems: List<ShopItem>) :
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

    class ViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: ShopItem) {
            binding.tvCartProductName.text = cartItem.name
            binding.tvCartProductQuantity.text = "Qty: ${cartItem.quantity}"
            binding.tvCartProductPrice.text = String.format("$%.2f", cartItem.price)
            // If images are part of the ShopItem, you could handle them here
            // Example: binding.ivCartItemImage.setImageResource(getImageResourceId(cartItem.image))
        }
    }
}
